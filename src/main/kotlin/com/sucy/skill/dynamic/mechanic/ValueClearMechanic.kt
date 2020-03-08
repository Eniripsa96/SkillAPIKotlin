package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class ValueClearMechanic : Mechanic() {
    override val key = "value clear"

    private var valueKey = "default"

    override fun initialize() {
        super.initialize()

        valueKey = metadata.getString("key", valueKey)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        recipient.metadata.remove(valueKey)
        recipient.values.remove(valueKey)
        return true
    }
}