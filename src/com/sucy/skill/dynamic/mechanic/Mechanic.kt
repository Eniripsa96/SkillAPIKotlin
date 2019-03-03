package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

abstract class Mechanic : Effect() {
    override val type = EffectType.MECHANIC

    private var applyToCaster = false
    private var applyToTarget = true
    protected var casterOnce = false

    override fun initialize() {
        val target = metadata.getString("target", "target").toLowerCase()
        applyToCaster = !target.equals("target")
        applyToTarget = !target.equals("caster")
        casterOnce = metadata.getBoolean("casterOnce", casterOnce)
    }

    override fun execute(caster: Actor, level: Int, targets: List<Actor>): Boolean {
        var result = false

        if (applyToCaster) {
            if (casterOnce) {
                return execute(caster, level, targets[0], caster)
            } else {
                targets.forEach { result = execute(caster, level, it, caster) }
            }
        } else {
            targets.forEach { result = execute(caster, level, it, it) }
        }
        return result
    }

    abstract fun execute(caster: Actor, level: Int, target: Actor, recipient: Actor): Boolean
}