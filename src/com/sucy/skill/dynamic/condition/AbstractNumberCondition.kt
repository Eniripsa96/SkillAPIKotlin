package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

abstract class AbstractNumberCondition : Condition() {
    private lateinit var min: DynamicFormula
    private lateinit var max: DynamicFormula

    override fun initialize() {
        super.initialize()

        min = metadata.getFormula("min", 0.0)
        max = metadata.getFormula("max", 9e20)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val min = compute(this.min, context.caster, target)
        val max = compute(this.max, context.caster, target)
        val actual = getValue(recipient, context.caster, target)
        return actual in min..max
    }

    abstract fun getValue(actor: Actor, caster: Actor, target: Actor): Double
}