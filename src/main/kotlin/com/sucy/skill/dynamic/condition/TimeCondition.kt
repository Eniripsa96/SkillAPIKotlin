package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class TimeCondition : AbstractNumberCondition() {
    override val key = "time"

    override fun getValue(actor: Actor, context: CastContext, target: Actor): Double {
        return actor.world.time.toDouble()
    }
}