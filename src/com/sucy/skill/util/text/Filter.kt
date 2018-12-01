package com.sucy.skill.util.text

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access

/**
 * SkillAPIKotlin © 2018
 */
class Filter(private val access: Access) {
    /**
     * Applies the filter to the provided text
     */
    fun apply(text: String, context: Actor? = null): String {
        val builder = StringBuilder()
        var start = 0
        var open = text.indexOf('{')
        while (open >= 0) {
            val close = text.indexOf('}', open + 2)
            if (close > 0) {
                // Apply the filter if it has a value for the key
                val replace = access.find(text.substring(open + 1, close), context)?.toString()
                if (replace != null) {
                    builder.append(text.substring(start, open))
                    builder.append(replace)
                    start = close + 1
                }
            }
            open = text.indexOf('{', close + 1)
        }
        if (start == 0) return text

        builder.append(text.substring(start))
        return builder.toString()
    }
}