package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class HealMechanic : Mechanic() {
    override val key = "heal"

    private lateinit var amount: DynamicFormula

    override fun initialize() {
        super.initialize()
        amount = metadata.getFormula("amount", 1.0)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val health = compute(amount, context.caster, target)
        recipient.heal(health)
        return true
    }
}