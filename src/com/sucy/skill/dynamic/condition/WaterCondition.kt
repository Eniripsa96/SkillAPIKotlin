package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class WaterCondition : Condition() {
    override val key = "water"

    private var inWater = true

    override fun initialize() {
        super.initialize()

        inWater = metadata.getBoolean("inWater", inWater)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return recipient.world.getBlock(recipient.location.coords).type.id.contains("WATER") == inWater
    }
}