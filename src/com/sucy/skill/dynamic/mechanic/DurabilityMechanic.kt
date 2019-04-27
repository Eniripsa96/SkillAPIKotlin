package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.enums.ItemSlot
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.text.enumName

class DurabilityMechanic : Mechanic() {
    override val key = "durability"

    private var itemSlot = ItemSlot.MAIN_HAND
    private var slot = 0

    private lateinit var formula: DynamicFormula

    override fun initialize() {
        super.initialize()

        formula = metadata.getFormula("amount", 1.0)
        itemSlot = ItemSlot.valueOf(metadata.getString("itemSlot", "MAIN_HAND").enumName())
        slot = metadata.getInt("slot", slot)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val item = itemSlot.getItem(recipient, slot) ?: return false
        val change = compute(formula, context.caster, target)

        if (item.durability < change) {
            itemSlot.setItem(recipient, null, slot)
        } else {
            item.durability = (item.durability - change).toShort()
        }
        return true
    }
}