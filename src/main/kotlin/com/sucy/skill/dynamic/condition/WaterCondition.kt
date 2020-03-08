package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class WaterCondition : Condition() {
    override val key = "water"
    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return recipient.world.getBlock(recipient.location.coords).type.id.contains("WATER")
    }
}