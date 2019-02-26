package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

abstract class Mechanic : Effect() {
    override val type = EffectType.MECHANIC

    private var applyToCaster = false
    private var applyToTarget = true

    override fun initialize() {
        val target = metadata.getString("target", "target").toLowerCase()
        applyToCaster = !target.equals("target")
        applyToTarget = !target.equals("caster")
    }

    override fun execute(caster: Actor, level: Int, targets: List<Actor>): Boolean {
        var result = false
        targets.forEach {
            if (applyToCaster) {
                result = execute(caster, level, caster) || result
            }
            if (applyToTarget) {
                result = execute(caster, level, it) || result
            }
        }
        return result
    }

    abstract fun execute(caster: Actor, level: Int, target: Actor): Boolean
}