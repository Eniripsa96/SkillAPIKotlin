package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class GroundCondition : Condition() {
    override val key = "ground"

    private var onGround = true

    override fun initialize() {
        super.initialize()
        onGround = metadata.getBoolean("onGround", onGround)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return recipient.isOnGround() == onGround
    }
}