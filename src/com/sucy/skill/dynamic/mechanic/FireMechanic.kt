package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.math.toTicks

class FireMechanic : Mechanic() {
    override val key = "fire"

    private lateinit var seconds: DynamicFormula

    override fun initialize() {
        super.initialize()
        seconds = metadata.getFormula("seconds", 3.0)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        recipient.setOnFire(compute(seconds, context.caster, target).toTicks())
        return true
    }
}