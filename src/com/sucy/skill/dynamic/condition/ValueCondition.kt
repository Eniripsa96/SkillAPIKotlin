package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class ValueCondition : AbstractNumberCondition() {
    override val key = "attribute"

    private lateinit var value: DynamicFormula

    override fun initialize() {
        super.initialize()

        value = metadata.getFormula("value")
    }

    override fun getValue(actor: Actor, context: CastContext, target: Actor): Double {
        return compute(value, context, target)
    }
}