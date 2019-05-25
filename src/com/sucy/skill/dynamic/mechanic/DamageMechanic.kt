package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.api.event.actor.DamageSource
import com.sucy.skill.util.math.formula.DynamicFormula

class DamageMechanic : Mechanic() {
    override val key = "damage"

    private var damageType = "default"
    private var trueDamage = false

    private lateinit var formula: DynamicFormula

    override fun initialize() {
        super.initialize()

        formula = metadata.getFormula("value", 1.0)
        damageType = metadata.getString("damageType", damageType)
        trueDamage = metadata.getBoolean("trueDamage", trueDamage)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val damage = compute(formula, context, target)
        recipient.damage(
                amount = damage,
                attacker = context.caster,
                source = DamageSource.SKILL,
                type = damageType,
                trueDamage = trueDamage)
        return true
    }
}