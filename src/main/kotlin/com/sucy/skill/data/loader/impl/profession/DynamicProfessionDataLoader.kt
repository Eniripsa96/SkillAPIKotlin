package com.sucy.skill.data.loader.impl.profession

import com.sucy.skill.api.profession.ExpSource
import com.sucy.skill.api.profession.Profession
import com.sucy.skill.api.profession.SkillTreePattern
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.impl.common.ItemDataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.data.loader.transform.legacy.LegacyDynamicProfessionTransformer
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.match
import com.sucy.skill.util.text.enumName

object DynamicProfessionDataLoader : DataLoader<Profession> {
    const val KEY = "key"
    const val NAME = "name"
    const val ICON = "icon"
    const val MAX_LEVEL = "maxLevel"
    const val SKILLS = "skills"
    const val PARENTS = "parents"
    const val UNUSABLE_ITEMS = "unusableItems"
    const val GROUP = "group"
    const val NEEDS_PERMISSION = "requiresPermissions"
    const val MAX_MANA = "maxMana"
    const val MAX_HEALTH = "maxHealth"
    const val MANA_REGEN = "manaRegen"
    const val PREFIX = "prefix"
    const val ACTION_BAR = "actionBar"
    const val MANA_NAME = "manaName"
    const val EXP = "expCurve"
    const val DESCRIPTION = "description"
    const val METADATA = "metadata"
    const val CONDITIONS = "levelConditions"
    const val TREE_PATTERN = "skillTreePattern"
    const val EXP_SOURCES = "expSources"

    override val requiredKeys: Array<String> = arrayOf(KEY)
    override val transformers: Map<Int, DataTransformer> = mapOf(1 to LegacyDynamicProfessionTransformer)

    override fun load(key: String, data: Data): Profession {
        val name = data.getString(KEY)!!
        val icon = ItemDataLoader.loadOrDefault(data.getSection(ICON))
        val maxLevel = data.getInt(MAX_LEVEL, 100)

        val profession = Profession(name, icon, maxLevel)

        profession.name = data.getString(NAME, profession.name)
        profession.skillNames.addAll(data.getStringList(SKILLS))
        profession.parentNames.addAll(data.getStringList(PARENTS))
        profession.unusableItems.addAll(data.getStringList(UNUSABLE_ITEMS).map { it.enumName() })
        profession.group = data.getString(GROUP, profession.group)
        profession.needsPermission = data.getBoolean(NEEDS_PERMISSION, profession.needsPermission)
        profession.maxMana = data.getFormula(MAX_MANA, 20.0)
        profession.maxHealth = data.getFormula(MAX_HEALTH, 20.0)
        profession.manaRegen = data.getFormula(MANA_REGEN, 1.0)
        profession.prefix = data.getString(PREFIX, profession.prefix)
        profession.actionBar = data.getString(ACTION_BAR, profession.actionBar)
        profession.manaName = data.getString(MANA_NAME, profession.manaName)
        profession.description.addAll(data.getStringList(DESCRIPTION))
        profession.metadata = data.getOrCreateSection(METADATA)
        profession.expCurve = data.getFormula(EXP, 1.0)
        profession.skillTreePattern = SkillTreePattern::class.match(data.getString(TREE_PATTERN), SkillTreePattern.FLOOD)
        profession.expSources.addAll(data.getStringList(EXP_SOURCES).map {
            ExpSource::class.match(it, ExpSource.SPECIAL)
        })

        return profession
    }

    override fun serialize(data: Profession): Data {
        val result = Data()
        result.set(KEY, data.key)
        result.set(NAME, data.name)
        result.set(ICON, ItemDataLoader.serialize(data.icon))
        result.set(MAX_LEVEL, data.maxLevel)
        result.set(SKILLS, data.skillNames.toList())
        result.set(PARENTS, data.parentNames.toList())
        result.set(UNUSABLE_ITEMS, data.unusableItems.toList())
        result.set(GROUP, data.group)
        result.set(NEEDS_PERMISSION, data.needsPermission)
        result.set(MAX_MANA, data.maxMana.expression)
        result.set(MAX_HEALTH, data.maxHealth.expression)
        result.set(MANA_REGEN, data.manaRegen.expression)
        result.set(PREFIX, data.prefix)
        result.set(ACTION_BAR, data.actionBar)
        result.set(MANA_NAME, data.manaName)
        result.set(DESCRIPTION, data.description)
        result.set(METADATA, data.metadata)
        result.set(EXP, data.expCurve.expression)

        return result
    }
}