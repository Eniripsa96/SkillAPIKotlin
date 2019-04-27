package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class HeldItemMechanic : Mechanic() {
    override val key = "held item"

    private var slot = 0

    override fun initialize() {
        super.initialize()

        slot = metadata.getInt("slot", slot)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        recipient.inventory.heldItemSlot = slot
        return true
    }
}