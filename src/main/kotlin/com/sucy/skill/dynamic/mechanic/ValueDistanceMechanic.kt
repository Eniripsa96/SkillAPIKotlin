package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class ValueDistanceMechanic : Mechanic() {
    override val key = "value distance"

    private var valueKey = "distance"

    override fun initialize() {
        super.initialize()
        valueKey = metadata.getString("key", valueKey)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        recipient.metadata[valueKey] = (recipient.location.coords - context.caster.location.coords).length
        return true
    }
}