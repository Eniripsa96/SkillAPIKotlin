package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.Status
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class InterruptMechanic : Mechanic() {
    override val key = "interrupt"

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        if (recipient.flags.isActive(Status.CHANNEL.flag)) {
            recipient.flags.clear(Status.CHANNEL.flag)
            return true
        }
        return false
    }
}