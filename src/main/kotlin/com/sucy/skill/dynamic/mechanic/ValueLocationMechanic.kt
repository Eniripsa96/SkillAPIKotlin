package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class ValueLocationMechanic : Mechanic() {
    override val key = "value location"

    private var valueKey = "location"

    override fun initialize() {
        super.initialize()
        valueKey = metadata.getString("key", valueKey)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        recipient.metadata[valueKey] = recipient.location
        return true
    }
}