package com.sucy.skill.dynamic

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
abstract class Effect {
    abstract val key: String
    abstract val type: EffectType

    private val children = ArrayList<Effect>()
    protected val metadata = Data()

    /**
     * Called after all data is loaded to parse any settings from the metadata
     */
    abstract fun initialize()

    /**
     * Called every time the skill is used
     */
    abstract fun execute(caster: Actor, level: Int, targets: List<Actor>): Boolean

    fun executeChildren(caster: Actor, level: Int, targets: List<Actor>): Boolean {
        if (targets.isEmpty()) return false

        var worked = false
        children.forEach {
            val counts = !it.metadata.getString("counts", "true").equals("false", true)
            val passed = it.execute(caster, level, targets)
            worked = (passed && counts) || worked
        }
        return worked
    }

    fun cleanUp(caster: Actor) {
        doCleanUp(caster)
        children.forEach { it.cleanUp(caster) }
    }

    /**
     * Called when a player logs off, dies, or unlearns a skill
     */
    open fun doCleanUp(caster: Actor) { }
}