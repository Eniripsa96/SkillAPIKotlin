package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula
import java.lang.Math.random

class ChanceCondition : Condition() {
    override val key = "chance"

    private lateinit var chance: DynamicFormula

    override fun initialize() {
        super.initialize()

        chance = metadata.getFormula("chance", 25.0)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val chance = compute(this.chance, context, target) / 100
        return random() < chance
    }
}