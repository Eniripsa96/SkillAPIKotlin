package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class CombatCondition : Condition() {
    override val key = "combat"

    private var inCombat = true
    private lateinit var seconds: DynamicFormula

    override fun initialize() {
        super.initialize()

        inCombat = metadata.getBoolean("combat", inCombat)
        seconds = metadata.getFormula("seconds", 10.0)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val seconds = compute(this.seconds, context, target)
        return recipient.isInCombat(seconds) == inCombat
    }
}