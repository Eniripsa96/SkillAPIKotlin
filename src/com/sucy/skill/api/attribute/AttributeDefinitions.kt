package com.sucy.skill.api.attribute

import com.sucy.skill.dynamic.Effect

data class AttributeDefinitions(val attributes: List<Attribute>) {
    private val byKey = attributes.map { it.key to it }.toMap()
    private val byKeyOrName = byKey + attributes.map { it.displayName.toLowerCase() to it }
    private val byStat: Map<String, List<Attribute>>
    private val byComponent: Map<String, List<Attribute>>

    operator fun get(key: String): Attribute? {
        return byKeyOrName[key.toLowerCase()]
    }

    fun forStat(key: String): List<Attribute> {
        return byStat[key.toLowerCase()] ?: emptyList()
    }

    fun forEffect(effect: Effect, valueKey: String): List<Attribute> {
        val id = "${effect.type.name.toLowerCase()}-${effect.key}-${valueKey.toLowerCase()}"
        return byComponent[id] ?: emptyList()
    }

    fun toKey(keyOrName: String): String {
        return byKeyOrName[keyOrName]?.key ?: keyOrName
    }

    init {
        byStat = attributes
                .map { it.statModifiers.keys.map { key -> key to it } }
                .flatten()
                .groupBy({ it.first }) { it.second }

        val byComponent = HashMap<String, List<Attribute>>()
        attributes.forEach { attribute ->
            attribute.dynamicModifiers.forEach { type, modifiers ->
                modifiers.forEach {
                    val list = byComponent.computeIfAbsent("$type-${it.key}".toLowerCase()) { ArrayList() }
                    (list as MutableList<Attribute>).add(attribute)
                }
            }
        }
        this.byComponent = byComponent
    }
}