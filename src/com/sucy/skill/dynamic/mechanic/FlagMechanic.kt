package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.math.toTicks

class FlagMechanic : Mechanic() {
    override val key = "flag"

    private var flag = "default"
    private lateinit var seconds: DynamicFormula

    override fun initialize() {
        flag = metadata.getString("flag", flag)
        seconds = metadata.getFormula("seconds", 3.0)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val ticks = compute(seconds, context.caster, target).toTicks()
        if (ticks > 0) {
            recipient.flags.set(flag, ticks)
        } else {
            recipient.flags.set(flag)
        }
        return true
    }
}
