package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.math.toTicks

class FlagToggleMechanic : Mechanic() {
    override val key = "flag toggle"

    private var flag = "default"

    override fun initialize() {
        flag = metadata.getString("flag", flag)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        if (recipient.flags.isActive(flag)) {
            recipient.flags.clear(flag)
        } else {
            recipient.flags.set(flag)
        }
        return true
    }
}
