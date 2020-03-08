package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.player.ManaCost
import com.sucy.skill.facade.api.event.player.ManaSource
import com.sucy.skill.util.math.formula.DynamicFormula

class ManaMechanic: Mechanic() {
    override val key = "mana"

    private lateinit var amount: DynamicFormula

    override fun initialize() {
        super.initialize()

        amount = metadata.getFormula("amount")
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val amount = compute(this.amount, context, target)
        return if (amount > 0) {
            target.giveMana(amount, ManaSource.SKILL_EFFECT)
        } else {
            target.takeMana(-amount, ManaCost.SKILL_EFFECT)
        }
    }
}