package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class ExplosionMechanic : Mechanic() {
    override val key: String = "explosion"

    private lateinit var power: DynamicFormula
    private var fire = false
    private var damageBlocks = false

    override fun initialize() {
        super.initialize()

        power = metadata.getFormula("power", 2.0)
        fire = metadata.getBoolean("fire", fire)
        damageBlocks = metadata.getBoolean("damageBlocks", damageBlocks)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val strength = compute(power, context.caster, target)
        SkillAPI.server.getWorld(recipient.location.world)
                .createExplosion(recipient.location.coords, strength, fire, damageBlocks)
        return true
    }
}