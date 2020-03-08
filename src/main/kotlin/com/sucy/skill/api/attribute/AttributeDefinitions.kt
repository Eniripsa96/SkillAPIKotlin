package com.sucy.skill.api.attribute

import com.sucy.skill.dynamic.Effect

data class AttributeDefinitions(val attributes: List<Attribute>) {
    private val byKey = attributes.map { it.key to it }.toMap()
    private val byKeyOrName = byKey + attributes.map { it.displayName.toLowerCase() to it }
    private val byStat: Map<String, StatAttributeSet>
    private val byComponent: Map<String, DynamicAttributeSet>

    operator fun get(key: String): Attribute? {
        return byKeyOrName[key.toLowerCase()]
    }

    fun forStat(key: String): StatAttributeSet {
        return byStat[key.toLowerCase()] ?: StatAttributeSet.EMPTY
    }

    fun forEffect(effect: Effect, valueKey: String): DynamicAttributeSet {
        val id = "${effect.type.name}-${effect.key}-$valueKey".toLowerCase()
        return byComponent[id] ?: DynamicAttributeSet.EMPTY
    }

    fun toKey(keyOrName: String): String {
        return byKeyOrName[keyOrName]?.key ?: keyOrName.toLowerCase()
    }

    init {
        byStat = attributes
                .map { it.statModifiers.keys.map { key -> key to it } }
                .flatten()
                .groupBy({ it.first }) { it.second }
                .mapValues { StatAttributeSet(it.key, it.value) }

        val byComponent = mutableMapOf<String, List<Attribute>>()
        attributes.forEach { attribute ->
            attribute.dynamicModifiers.forEach { type, modifiers ->
                modifiers.forEach {
                    val list = byComponent.computeIfAbsent("$type-${it.key}".toLowerCase()) { mutableListOf() }
                    (list as MutableList<Attribute>).add(attribute)
                }
            }
        }
        this.byComponent = byComponent.mapValues {
            DynamicAttributeSet(it.key.substring(it.key.indexOf('-') + 1), it.value)
        }
    }
}