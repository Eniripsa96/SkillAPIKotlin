package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class FireCondition : Condition() {
    override val key = "fire"

    private var onFire = true

    override fun initialize() {
        super.initialize()
        onFire = metadata.getBoolean("onFire", onFire)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return (recipient.fireTicks > 0) == onFire
    }
}