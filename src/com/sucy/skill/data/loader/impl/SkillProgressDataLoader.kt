package com.sucy.skill.data.loader.impl

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.api.skill.SkillProgress
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.internal.data.InternalItem
import com.sucy.skill.util.io.Data

object SkillProgressDataLoader : LevelProgressDataLoader<SkillProgress, Skill>() {
    private val ITEM = InternalItem("PUMPKIN")

    private const val NAME = "name"
    private const val SOURCES = "sources"
    private const val COOLDOWN = "cooldown"

    override val requiredKeys: Array<String> = arrayOf(NAME)
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(data: Data): SkillProgress {
        val name = data.getString(NAME)!!
        val skill = SkillAPI.registry.getSkill(name) ?: MissingSkill(name)
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

    class MissingSkill(name: String) : Skill(name, ITEM, 1) {
        override fun apply(caster: Actor, level: Int, target: Actor): Boolean = false
    }
}