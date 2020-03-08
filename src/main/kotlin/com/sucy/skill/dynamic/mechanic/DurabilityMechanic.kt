package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.common.ItemSlot
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import com.sucy.skill.util.math.formula.DynamicFormula

class DurabilityMechanic : Mechanic() {
    override val key = "durability"

    private var itemSlot = ItemSlot.MAIN_HAND
    private var slot = 0

    private lateinit var formula: DynamicFormula

    override fun initialize() {
        super.initialize()

        formula = metadata.getFormula("amount", 1.0)
        itemSlot = ItemSlot::class.match(metadata.getString("itemSlot", "MAIN_HAND"), ItemSlot.MAIN_HAND)
        slot = metadata.getInt("slot", slot)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val item = itemSlot.getItem(recipient, slot) ?: return false
        val change = compute(formula, context, target)

        if (item.durability < change) {
            itemSlot.setItem(recipient, null, slot)
        } else {
            item.durability = (item.durability - change).toShort()
        }
        return true
    }
}