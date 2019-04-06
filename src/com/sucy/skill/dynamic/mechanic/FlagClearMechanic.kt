package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class FlagClearMechanic : Mechanic() {
    override val key = "flag clear"

    private var flag = "default"

    override fun initialize() {
        flag = metadata.getString("flag", flag)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        recipient.flags.clear(flag)
        return true
    }
}
