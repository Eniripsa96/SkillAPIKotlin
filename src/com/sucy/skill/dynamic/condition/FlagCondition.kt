package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class FlagCondition : Condition() {
    override val key = "flag"

    private var flag = "default"
    private var set = true

    override fun initialize() {
        super.initialize()

        flag = metadata.getString("flag", flag)
        set = metadata.getBoolean("set", set)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return recipient.flags.isActive(flag) == set
    }
}