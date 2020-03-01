package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

abstract class Condition : Effect() {
    override val type = EffectType.CONDITION

    protected var applyToCaster = false
    protected var elseIf = false
    protected var negated = false

    override fun initialize() {
        val target = metadata.getString("apply-to", "target").toLowerCase()
        applyToCaster = target != "target"
        elseIf = metadata.getBoolean("else-if", elseIf)
        negated = metadata.getBoolean("negated", negated)
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        val previous = context.failures.pop()
        val actorSet = if (elseIf) previous else targets

        val (passed, failed) = actorSet.partition {
            val recipient = if (applyToCaster) context.caster else it
            recipient.exists && !recipient.dead && (matches(context, it, recipient) != negated)
        }

        context.failures.push(failed)
        return passed.isNotEmpty() && executeChildren(context, passed)
    }

    abstract fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean
}