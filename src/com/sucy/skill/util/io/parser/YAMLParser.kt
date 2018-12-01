package com.sucy.skill.util.io.parser

import com.sucy.skill.util.io.Data
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * SkillAPIKotlin © 2018
 */
class YAMLParser : Parser() {
    private val emptyObject = Pattern.compile("[^:]+:\\s*\\{\\s*}$")!!
    private val number = Pattern.compile("[+-]?[0-9]+([.,][0-9]+)?")

    override fun serialize(data: Data, builder: StringBuilder, indent: Int, quote: Char) {
        // Create spacing to use
        val spacing = StringBuilder()
        for (i in 0 until indent) {
            spacing.append(' ')
        }

        for (key in data.keys()) {
            // Comments first
            if (data.comments.containsKey(key)) {
                val lines = data.comments[key]
                for (line in lines!!) {
                    if (line.isEmpty()) {
                        builder.append('\n')
                        continue
                    }
                    builder.append(spacing)
                    builder.append('#')
                    builder.append(line)
                    builder.append('\n')
                }
            }

            // Write the key
            builder.append(spacing)
            builder.append(key)
            builder.append(":")

            val value = data.get(key, null)

            // Empty section
            if (value == null) {
                builder.append(" {}\n")
            } else if (value is Data) {
                if (value.keys().isEmpty()) {
                    builder.append(" {}\n")
                } else {
                    builder.append('\n')
                    serialize(value, builder, indent + 2, quote)
                }
            } else if (value is List<*>) {
                val list = value as List<*>?
                if (list!!.isEmpty()) {
                    builder.append(" []")
                    builder.append('\n')
                } else {
                    builder.append('\n')
                    for (item in list) {
                        builder.append(spacing)
                        builder.append("- ")
                        writeValue(builder, item!!, quote)
                        builder.append('\n')
                    }
                }
            } else {
                builder.append(' ')
                writeValue(builder, value, quote)
                builder.append('\n')
            }
        }
    }

    /**
     * Writes a single value to a string builder
     *
     * @param builder string builder to collect the data
     * @param value   value to write to the builder
     * @param quote   quote to use for strings
     */
    private fun writeValue(builder: StringBuilder, value: Any, quote: Char) {
        val fallback = if (quote == '"') '\'' else '"'
        when {
            number.matcher(value.toString()).matches() -> builder.append(value.toString())
            value.toString().indexOf(quote) >= 0 -> {
                builder.append(fallback)
                builder.append(value.toString())
                builder.append(fallback)
            }
            else -> {
                builder.append(quote)
                builder.append(value.toString())
                builder.append(quote)
            }
        }
    }

    override fun parse(data: String): Data {
        val lines = data.lines()

        val comments = ArrayList<String>()
        val stack = Stack<Data>()

        var indent = 0
        var target = Data()
        var index = 0
        while (index < lines.size) {
            val line = lines[index++]
            val trimmed = line.trim()
            if (trimmed.isEmpty()) {
                comments.add("")
                continue
            }

            if (trimmed.startsWith('#')) {
                comments.add(trimmed.substring(1))
                continue
            }

            val spaces = countSpaces(line)
            if (spaces < indent && spaces % 2 == 0) {
                while (indent > spaces) {
                    indent -= 2
                    target = stack.pop()
                }
            } else if (spaces != indent) {
                throw IllegalArgumentException("Invalid indentation at line $index")
            }

            val key = parseKey(trimmed, index)

            target.comments[key] = ArrayList(comments)
            comments.clear()

            if (emptyObject.matcher(trimmed).matches()) {
                target.createSection(key)
            } else if (key.length == trimmed.length - 1) {
                var next = nextLine(lines, index)
                if (countSpaces(lines[next]) == indent + 2) {
                    indent += 2
                    val section = target.createSection(key)
                    stack.push(target)
                    target = section
                } else {
                    val list = ArrayList<String>()
                    while (next < lines.size && countSpaces(lines[next]) == indent && lines[next][indent] == '-') {
                        val raw = lines[next].substring(indent + 1)
                        index = next + 1
                        next = nextLine(lines, index)
                        list.add(parseString(raw))
                    }
                    if (list.isEmpty()) {
                        throw java.lang.IllegalArgumentException("Invalid empty key on line $index")
                    }
                    target.set(key, list)
                }
            } else {
                val raw = trimmed.substring(trimmed.indexOf(':', key.length) + 1)
                target.set(key, parseString(raw))
            }
        }
        while (!stack.isEmpty()) target = stack.pop()
        return target
    }

    private fun nextLine(lines: List<String>, index: Int): Int {
        var next = index
        while (next < lines.size && (lines[next].trim().startsWith('#') || lines[next].isBlank())) {
            next++
        }
        return next
    }

    private fun countSpaces(line: String): Int {
        var count = 0
        while (line.length > count && line[count] == ' ') count++
        return count
    }

    private fun parseKey(trimmed: String, index: Int): String {
        return when {
            trimmed.startsWith('"') -> {
                val end = trimmed.indexOf("\":", 1)
                if (end < 0) throw IllegalArgumentException("Invalid key on line $index")
                trimmed.substring(1, end)
            }
            trimmed.startsWith('\'') -> {
                val end = trimmed.indexOf("':", 1)
                if (end < 0) throw IllegalArgumentException("Invalid key on line $index")
                trimmed.substring(1, end)
            }
            else -> {
                val end = trimmed.indexOf(':')
                if (end <= 0) throw IllegalArgumentException("Invalid key on line $index")
                trimmed.substring(0, end)
            }
        }
    }

    private fun parseString(data: String): String {
        val trimmed = data.trim()
        return if (trimmed.startsWith('\'') && trimmed.endsWith('\'')) {
            trimmed.substring(1, trimmed.length - 1)
        } else if (trimmed.startsWith('"') && trimmed.endsWith('"')) {
            trimmed.substring(1, trimmed.length - 1)
        } else {
            trimmed
        }
    }
}
