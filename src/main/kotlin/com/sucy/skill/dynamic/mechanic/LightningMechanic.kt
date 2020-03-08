package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class LightningMechanic : Mechanic() {
    override val key = "lightning"

    private var damage = true

    override fun initialize() {
        super.initialize()

        damage = metadata.getBoolean("damage", damage)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        if (damage) target.world.strikeLightning(recipient.location.coords)
        else target.world.playLightningEffect(recipient.location.coords)
        return true
    }
}