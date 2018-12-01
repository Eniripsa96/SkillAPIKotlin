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

    fun doCleanUp(caster: Actor) { }
}