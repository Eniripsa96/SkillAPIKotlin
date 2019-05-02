package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.common.ItemContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class ItemCondition : Condition() {
    override val key = "item"

    lateinit var itemContext: ItemContext
    lateinit var amount: DynamicFormula

    override fun initialize() {
        super.initialize()

        itemContext = ItemContext(metadata)
        amount = metadata.getFormula("amount", 1.0)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        var required = amount.evaluate(context.caster, target)
        return itemContext.findItems(recipient, context.caster, target) {
            required -= it.amount
            required <= 0
        }
    }
}