package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

abstract class Condition : Effect() {
    override val type = EffectType.CONDITION

    private var applyToCaster = false

    override fun initialize() {
        val target = metadata.getString("applyTo", "target").toLowerCase()
        applyToCaster = target != "target"
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        val filtered = targets.filter {
            val recipient = if (applyToCaster) context.caster else it
            recipient.exists && !recipient.dead && matches(context, it, recipient)
        }

        return filtered.isNotEmpty() && executeChildren(context, filtered)
    }

    abstract fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean
}