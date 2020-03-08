package com.sucy.skill.data.loader.impl.profession

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.profession.Profession
import com.sucy.skill.api.profession.ProfessionProgress
import com.sucy.skill.data.loader.impl.LevelProgressDataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.facade.api.data.inventory.VanillaItemType
import com.sucy.skill.facade.internal.data.InternalItem
import com.sucy.skill.util.io.Data

object ProfessionProgressDataLoader : LevelProgressDataLoader<ProfessionProgress, Profession>() {
    const val NAME = "name"
    val ITEM = InternalItem(VanillaItemType.PUMPKIN)

    override val requiredKeys: Array<String> = arrayOf()
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(key: String, data: Data): ProfessionProgress {
        val profession = SkillAPI.registry.getProfession(key) ?: MissingProfession(key)
        val result = ProfessionProgress(profession)

        super.load(result, data)

        return result
    }

    override fun serialize(data: ProfessionProgress): Data {
        val result = super.serialize(data)
        result.set(NAME, data.data.name)
        return result
    }

    override fun getKey(data: ProfessionProgress): String? {
        return data.data.key
    }

    class MissingProfession(name: String) : Profession(name, ITEM, 1)
}