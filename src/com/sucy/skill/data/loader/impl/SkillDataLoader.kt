package com.sucy.skill.data.loader.impl

import com.sucy.skill.api.skill.Skill
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.data.loader.transform.legacy.LegacySkillTranformer
import com.sucy.skill.dynamic.DynamicSkill
import com.sucy.skill.util.io.Data

class SkillDataLoader : DataLoader<Skill> {
    override val transformers: Map<Int, DataTransformer> = mapOf(Pair(1, LegacySkillTranformer()))
    override val requiredKeys: Array<String> = arrayOf(NAME)

    override fun load(data: Data): Skill {
        val result = DynamicSkill(
                data.getString(NAME)!!,
                ItemDataLoader().loadOrDefault(data.getSection(ICON)),
                data.getInt(MAX_LEVEL, 1))
        // TODO - implement
        return result
    }

    companion object {
        const val NAME = "name"
        const val MAX_LEVEL = "maxLevel"
        const val ICON = "icon"
    }
}