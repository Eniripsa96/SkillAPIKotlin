package com.sucy.skill.api.attribute

/**
 * SkillAPIKotlin © 2018
 */
class AttributeSet {
    private val attributes = HashMap<String, AttributeValue>()

    /**
     * Fetches data for an attribute using a given [key]
     */
    fun AttributeSet.get(key: String): AttributeValue {
        return attributes.computeIfAbsent(key) { AttributeValue() }
    }

    /**
     * Clears all bonuses from the given [source]
     */
    fun clear(source: String) {
        attributes.values.forEach { it.clear(source) }
    }

    /**
     * Clears bonuses from all sources starting with the [prefix]
     */
    fun clearByPrefix(prefix: String) {
        attributes.values.forEach { it.clearByPrefix(prefix) }
    }
}