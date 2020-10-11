package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class DirectionCondition : Condition() {
    override val key = "direction"

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        if (context.caster == target) return false

        val source = if (recipient == target) context.caster else target
        val facing = source.location.forward
        val relative = recipient.location.coords - source.location.coords
        return facing.dot(relative) >= 0
    }
}