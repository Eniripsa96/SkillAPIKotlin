package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.Value
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.common.ItemContext
import com.sucy.skill.facade.api.entity.Actor

class ValueLoreMechanic : Mechanic() {
    override val key = "value lore"

    private lateinit var itemContext: ItemContext
    private var valueKey = "default"

    override fun initialize() {
        super.initialize()
        itemContext = ItemContext(metadata)
        valueKey = metadata.getString("value-key", valueKey)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val found = itemContext.getValue(recipient)
        if (found == 0.0) return false

        recipient.values.remove(valueKey)
        val value = recipient.values[valueKey]
        value.setTo(found, valueKey)
        return true
    }
}