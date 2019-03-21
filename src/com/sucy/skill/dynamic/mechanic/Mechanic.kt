package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
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

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        var result = false

        if (applyToCaster) {
            if (casterOnce) {
                return execute(context, targets[0], context.caster)
            } else {
                targets.forEach { result = execute(context, it, context.caster) }
            }
        } else {
            targets.forEach { result = execute(context, it, it) }
        }
        return result
    }

    abstract fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean
}