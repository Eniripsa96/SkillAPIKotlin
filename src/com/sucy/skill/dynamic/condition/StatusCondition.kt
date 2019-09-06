package com.sucy.skill.dynamic.condition

import com.sucy.skill.api.values.Status
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class StatusCondition : Condition() {
    override val key = "status"

    private var statuses = listOf(Status.STUN.name)
    private var set = true

    override fun initialize() {
        super.initialize()

        statuses = metadata.getStringList("status", statuses)
        set = metadata.getBoolean("set", set)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return statuses.any { recipient.flags.isActive(it) } == set
    }
}