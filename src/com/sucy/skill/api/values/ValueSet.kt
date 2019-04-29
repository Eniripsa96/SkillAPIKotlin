package com.sucy.skill.api.values

/**
 * SkillAPIKotlin © 2018
 */
class ValueSet {
    private val values = HashMap<String, Value>()

    /**
     * Fetches data for an attribute using a given [key]
     */
    operator fun get(key: String): Value {
        return values.computeIfAbsent(key) { Value() }
    }

    /**
     * Returns true if there is a value existing for the given key
     */
    fun has(key: String): Boolean {
        return values.containsKey(key)
    }

    /**
     * Clears the current value under a given key
     */
    fun remove(key: String): Value? = values.remove(key)

    /**
     * Clears all bonuses from the given [source]
     */
    fun clear(source: String) {
        values.values.forEach { it.clear(source) }
    }

    /**
     * Clears bonuses from all sources starting with the [prefix]
     */
    fun clearByPrefix(prefix: String) {
        values.values.forEach { it.clearByPrefix(prefix) }
    }
}