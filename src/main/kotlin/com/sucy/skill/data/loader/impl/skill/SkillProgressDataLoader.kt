package com.sucy.skill.data.loader.impl.skill

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.api.skill.SkillProgress
import com.sucy.skill.data.loader.impl.LevelProgressDataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.facade.api.data.inventory.VanillaItemType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.internal.data.InternalItem
import com.sucy.skill.util.io.Data

object SkillProgressDataLoader : LevelProgressDataLoader<SkillProgress, Skill>() {
    private val ITEM = InternalItem(VanillaItemType.PUMPKIN)

    private const val NAME = "name"
    private const val SOURCES = "sources"
    private const val COOLDOWN = "cooldown"

    override val requiredKeys: Array<String> = arrayOf(NAME)
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(key: String, data: Data): SkillProgress {
        val skill = SkillAPI.registry.getSkill(key) ?: MissingSkill(key)
        val result = SkillProgress(skill)

        super.load(result, data)

        result.cooldown.time = data.getDouble(COOLDOWN)
        result.sources.addAll(data.getStringList(SOURCES))

        return result
    }

    override fun serialize(data: SkillProgress): Data {
        val result = super.serialize(data)
        result.set(NAME, data.data.name)
        result.set(SOURCES, data.sources.toList())
        result.set(COOLDOWN, data.cooldown.time)
        return result
    }

    override fun getKey(data: SkillProgress): String? {
        return data.data.key
    }

    class MissingSkill(name: String) : Skill(name, ITEM, 1) {
        override fun apply(caster: Actor, level: Int, target: Actor): Boolean = false
    }
}