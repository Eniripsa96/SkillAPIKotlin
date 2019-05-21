package com.sucy.skill.data.loader.impl.profession

import com.sucy.skill.api.profession.Profession
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.impl.common.ItemDataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.text.enumName

object DynamicProfessionDataLoader : DataLoader<Profession> {
    private const val NAME = "name"
    private const val ICON = "icon"
    private const val MAX_LEVEL = "maxLevel"
    private const val SKILLS = "skills"
    private const val PARENTS = "parents"
    private const val UNUSABLE_ITEMS = "unusableItems"
    private const val GROUP = "group"
    private const val NEEDS_PERMISSION = "requiresPermissions"
    private const val MAX_MANA = "maxMana"
    private const val MAX_HEALTH = "maxHealth"
    private const val MANA_REGEN = "manaRegen"
    private const val PREFIX = "prefix"
    private const val ACTION_BAR = "actionBar"
    private const val MANA_NAME = "manaName"

    override val requiredKeys: Array<String> = arrayOf(NAME)
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(key: String, data: Data): Profession {
        val name = data.getString(NAME)!!
        val icon = ItemDataLoader.loadOrDefault(data.getSection(ICON))
        val maxLevel = data.getInt(MAX_LEVEL, 100)

        val profession = Profession(name, icon, maxLevel)

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

        return profession
    }

    override fun serialize(data: Profession): Data {
        val result = Data()
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

        return result
    }
}