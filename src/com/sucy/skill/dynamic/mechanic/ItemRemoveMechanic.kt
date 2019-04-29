package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.common.ItemContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class ItemRemoveMechanic : Mechanic() {
    override val key = "item remove"

    private lateinit var itemContext: ItemContext
    private lateinit var amount: DynamicFormula

    override fun initialize() {
        super.initialize()

        itemContext = ItemContext(metadata)
        amount = metadata.getFormula("amount")
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        var remaining = compute(amount, context.caster, target).toInt()
        return itemContext.findSlots(recipient, context.caster, target) { inventory, item, slot ->
            if (item.amount < remaining) {
                remaining -= item.amount
                inventory[slot] = null
                false
            } else {
                item.amount -= remaining
                inventory[slot] = item
                true
            }
        }
    }
}