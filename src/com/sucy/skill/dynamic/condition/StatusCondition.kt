package com.sucy.skill.dynamic.condition

import com.sucy.skill.api.values.Status
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match

class StatusCondition : Condition() {
    override val key = "status"

    private var statuses = listOf(Status.STUN)
    private var set = true

    override fun initialize() {
        super.initialize()

        statuses = metadata.getStringList("status", statuses.map { it.name }).mapNotNull { Status::class.match(it) }
        set = metadata.getBoolean("set", set)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return statuses.any { recipient.flags.isActive(it.name) } == set
    }
}