package com.sucy.skill.data.loader.transform.legacy

import com.sucy.skill.api.profession.ExpSource
import com.sucy.skill.data.loader.impl.common.ItemDataLoader
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.ACTION_BAR
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.EXP_SOURCES
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.GROUP
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.ICON
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.KEY
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.MANA_NAME
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.MANA_REGEN
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.MAX_HEALTH
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.MAX_LEVEL
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.MAX_MANA
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.METADATA
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.NAME
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.NEEDS_PERMISSION
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.PARENTS
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.PREFIX
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.SKILLS
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.TREE_PATTERN
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader.UNUSABLE_ITEMS
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

object LegacyDynamicProfessionTransformer : DataTransformer {
    override fun transform(key: String, data: Data): Data {
        val result = Data()

        val details = if (data.has(NAME)) {
            result.set(KEY, key)
            data
        } else {
            val found = data.keys().find {
                val section = data.getSection(it)
                section != null && section.has(NAME)
            } ?: throw IllegalArgumentException("Invalid legacy config for $key - does not have a name")
            result.set(KEY, found)
            data.getSection(found)!!
        }

        result.set(NAME, details.getString(NAME, key))
        result.set(MAX_LEVEL, details.getInt("max-level"))
        result.set(SKILLS, details.getStringList("skills"))
        result.set(PARENTS, listOf(details.getString("parent", "")).filter { it.isNotBlank() })
        result.set(UNUSABLE_ITEMS, details.getStringList("blacklist"))
        result.set(GROUP, details.getString("group", "class"))
        result.set(NEEDS_PERMISSION, details.getBoolean("needs-permission", false))
        result.set(MANA_REGEN, details.getDouble("mana-regen"))
        result.set(PREFIX, details.getString("prefix", ""))
        result.set(ACTION_BAR, details.getString("action-bar", ""))
        result.set(MANA_NAME, details.getString("mana", ""))
        result.set(TREE_PATTERN, details.getString("tree", "FLOOD"))

        val attributes = details.getOrCreateSection("attributes")
        result.set(MAX_MANA, parseFormula(attributes, "mana", 20.0))
        result.set(MAX_HEALTH, parseFormula(attributes, "health", 20.0))

        val rootKeys = HashSet<String>()
        attributes.forEach(setOf("health-base", "health-scale", "mana-base", "mana-scale")) {
            rootKeys.add(it.replace(Regex("(-scale|-base)"), ""))
        }
        val metadata = result.createSection(METADATA)
        rootKeys.forEach {
            metadata.set(it, parseFormula(attributes, it, 0.0))
        }

        val iconData = result.createSection(ICON)
        result.getString("icon")?.let { iconData.set(ItemDataLoader.TYPE, it) }
        iconData.set(ItemDataLoader.DURABILITY, result.getInt("icon-durability"))
        iconData.set(ItemDataLoader.DATA, result.getInt("icon-data"))
        iconData.set(ItemDataLoader.LORE, result.getStringList("icon-lore"))

        val expSources = details.getInt("exp-source")
        result.set(EXP_SOURCES, ExpSource.values().filter {
            ((1 shl it.ordinal) and expSources) > 0
        }.map { it.name })

        return result
    }

    private fun parseFormula(data: Data, key: String, defaultBase: Double): String {
        val base = data.getDouble("$key-base", defaultBase)
        val scale = data.getDouble("$key-scale")
        return if (scale != 0.0) {
            "$base + $scale * lvl"
        } else {
            "$base"
        }
    }
}