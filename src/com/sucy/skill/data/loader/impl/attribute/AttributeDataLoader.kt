package com.sucy.skill.data.loader.impl.attribute

import com.sucy.skill.api.attribute.Attribute
import com.sucy.skill.api.attribute.Modifier
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.impl.common.ItemDataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.math.formula.Formula

object AttributeDataLoader : DataLoader<Attribute> {
    const val DISPLAY_NAME = "name"
    const val ICON = "icon"
    const val MAX = "max"
    const val EFFECTS = "skillEffects"
    const val STATS = "stats"

    private val MODIFIER_KEYS = mutableListOf("v", "a")

    override val requiredKeys = arrayOf<String>()
    override val transformers = mapOf<Int, DataTransformer>()

    override fun load(key: String, data: Data): Attribute {
        val effectModifiersData = data.getOrCreateSection(EFFECTS)
        val effectModifiers = EffectType.values()
                .map { it to loadEffectModifiers(effectModifiersData, it) }
                .filter { it.second.isNotEmpty() }
                .toMap()

        val statModifiersData = data.getOrCreateSection(STATS)
        val statModifiers = statModifiersData.keys().mapNotNull {
            val expression = statModifiersData.getString(it) ?: return@mapNotNull null
            it to Formula(expression, MODIFIER_KEYS)
        }.toMap()

        return Attribute(
                key = key,
                displayName = data.getString(DISPLAY_NAME, key),
                icon = ItemDataLoader.loadOrDefault(data.getSection(ICON)),
                max = data.getInt(MAX, 0),
                dynamicModifiers = effectModifiers,
                statModifiers = statModifiers
        )
    }

    private fun loadEffectModifiers(data: Data, type: EffectType): Map<String, List<Modifier>> {
        val section = data.getOrCreateSection(type.name.toLowerCase())
        return section.keys().map {
            it.toLowerCase() to section.getStringList(it).map(this::parseModifier)
        }.toMap()
    }

    private fun parseModifier(data: String): Modifier {
        val parts = data.split(":")
        return Modifier(
                formula = Formula(parts[0], MODIFIER_KEYS),
                conditions = parts.subList(1, parts.size)
                        .map { it.split('=') }
                        .filter { it.size == 2 }
                        .map { it[0] to it[1] }
                        .toMap()
        )
    }

    override fun serialize(data: Attribute): Data {
        val result = Data()
        result.set(DISPLAY_NAME, data.displayName)
        result.set(MAX, data.max)
        result.set(ICON, ItemDataLoader.serialize(data.icon))

        if (data.statModifiers.isNotEmpty()) {
            val statSection = result.createSection(STATS)
            data.statModifiers.forEach { statSection.set(it.key, it.value.expression) }
        }

        if (data.dynamicModifiers.isNotEmpty()) {
            val effectSection = result.createSection(EFFECTS)
            data.dynamicModifiers.filter { it.value.isNotEmpty() }.forEach { entry ->
                val typeSection = effectSection.createSection(entry.key.name.toLowerCase())
                entry.value.forEach { typeSection.set(it.key, it.value.map(this::serializeModifier)) }
            }
        }

        return result
    }

    private fun serializeModifier(modifier: Modifier): String {
        if (modifier.conditions.isEmpty()) return modifier.formula.expression

        return modifier.conditions
                .map { "${it.key}=${it.value}" }
                .joinToString(":", prefix = "${modifier.formula.expression}:")
    }
}