package com.sucy.skill.data.loader.impl.skill

import com.sucy.skill.api.skill.Skill
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.impl.common.ItemDataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.data.loader.transform.legacy.LegacySkillTransformer
import com.sucy.skill.dynamic.DynamicSkill
import com.sucy.skill.util.io.Data

object DynamicSkillDataLoader : DataLoader<Skill> {
    private const val NAME = "name"
    private const val MAX_LEVEL = "maxLevel"
    private const val ICON = "icon"

    override val transformers: Map<Int, DataTransformer> = mapOf(1 to LegacySkillTransformer)
    override val requiredKeys: Array<String> = arrayOf(NAME)

    override fun load(key: String, data: Data): Skill {
        val result = DynamicSkill(
                data.getString(NAME)!!,
                ItemDataLoader.loadOrDefault(data.getSection(ICON)),
                data.getInt(MAX_LEVEL, 1))
        return result
    }

    override fun serialize(data: Skill): Data {
        TODO("not implemented")
    }
}