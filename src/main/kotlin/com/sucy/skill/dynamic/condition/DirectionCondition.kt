package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class DirectionCondition : Condition() {
    override val key = "direction"

    private var inFront = false

    override fun initialize() {
        super.initialize()

        inFront = metadata.getBoolean("in-front", inFront)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        if (context.caster == target) return false

        val source = if (recipient == target) {
            context.caster
        } else {
            target
        }

        val facing = source.location.forward
        val relative = recipient.location.coords - source.location.coords
        val isInFront = facing.dot(relative) >= 0

        return isInFront == inFront
    }
}