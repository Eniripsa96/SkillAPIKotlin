package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class CastLevelCondition : Condition() {
    override val key = "cast level"

    private var min = 1
    private var max = 999

    override fun initialize() {
        super.initialize()

        min = metadata.getInt("min", min)
        max = metadata.getInt("max", max)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return context.level in min..max
    }
}