package com.sucy.skill.util.text.context

open class FilterContext<T>(
        val key: String,
        val subject: T,
        val filters: Map<String, (T) -> String>,
        val default: (T, String) -> String? = { _, _ -> null }
) {
    fun getValue(key: String): String? {
        return filters[key]?.invoke(subject) ?: default.invoke(subject, key)
    }
}