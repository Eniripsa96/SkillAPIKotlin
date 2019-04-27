package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class HealthSetMechanic : Mechanic() {
    override val key = "health set"

    private lateinit var formula: DynamicFormula

    override fun initialize() {
        super.initialize()

        formula = metadata.getFormula("health", 1.0)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val result = compute(formula, context.caster, target)
        recipient.health = result
        return true
    }
}