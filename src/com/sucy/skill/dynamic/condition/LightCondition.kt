package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class LightCondition : AbstractNumberCondition() {
    override val key = "light"

    override fun getValue(actor: Actor, context: CastContext, target: Actor): Double {
        return actor.world.getBlock(actor.location.coords).lightLevel.toDouble()
    }
}