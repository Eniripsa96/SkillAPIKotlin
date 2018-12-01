package com.sucy.skill.util.io.parser

import com.sucy.skill.util.io.Data
import java.io.File
import java.nio.charset.StandardCharsets.UTF_8

/**
 * SkillAPIKotlin © 2018
 */
abstract class Parser {
    fun load(file: String): Data {
        return load(File(file))
    }

    fun load(file: File): Data {
        return try {
            val contents = file.readText(UTF_8)
            parse(contents)
        } catch (ex: Exception) {
            Data()
        }
    }

    fun load(owner: Any, path: String): Data {
        return try {
            var contents = ""
            owner.javaClass.getResourceAsStream("/$path").use {
                contents = it.bufferedReader(UTF_8).readText()
            }

            if (contents.isBlank()) {
                return Data()
            }

            parse(contents)
        } catch (ex: Exception) {
            Data()
        }
    }

    fun serialize(data: Data, quote: Char = '"') : String {
        val builder = StringBuilder()
        serialize(data, builder, 0, quote)
        return builder.toString()
    }

    fun save(data: Data, file: String, quote: Char = '"') {
        save(data, File(file), quote)
    }

    fun save(data: Data, file: File, quote: Char = '"') {
        file.parentFile.mkdirs()
        file.writeText(serialize(data, quote))
    }

    abstract fun parse(data: String): Data
    abstract fun serialize(data: Data, builder: StringBuilder, indent: Int, quote: Char)
}
