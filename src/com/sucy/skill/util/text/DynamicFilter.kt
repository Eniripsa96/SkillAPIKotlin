package com.sucy.skill.util.text

import com.sucy.skill.util.text.context.FilterContext

object DynamicFilter {

    fun apply(text: String, context: FilterContext<*>, vararg contexts: FilterContext<*>): String {
        val builder = StringBuilder()
        var start = 0
        var open = text.indexOf('{')
        var close = open
        val contextMap = contexts.map { it.key to it }.toMap()
        while (open >= 0 && close > 0) {
            close = text.indexOf('}', open + 2)
            if (close > 0) {
                // Apply the filter if it has a value for the key
                val key = text.substring(open + 1, close)
                val parts = key.split(".", limit = 2)

                val replace = if (parts.size == 1) {
                    context.getValue(key)
                } else {
                    contextMap[parts[0]]?.getValue(parts[1])
                }

                if (replace != null) {
                    builder.append(text.substring(start, open))
                    builder.append(replace)
                    start = close + 1
                }

                open = text.indexOf('{', close + 1)
            }
        }
        if (start == 0) return text

        builder.append(text.substring(start))
        return builder.toString()
    }
}