package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.event.actor.ActorDamagedEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
abstract class BaseDamageTrigger : Trigger<ActorDamagedEvent> {
    abstract val requireSkillDamage: Boolean

    /** {@inheritDoc}  */
    override val event: Class<ActorDamagedEvent>
        get() = ActorDamagedEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDamagedEvent, level: Int, settings: Data): Boolean {
        val type = settings.getString("type", "both")
        val min = settings.getDouble("dmg-min")
        val max = settings.getDouble("dmg-max")
        val projectile = event.source == ActorDamagedEvent.DamageSource.PROJECTILE
        return event.amount in min..max &&
                (event.source == ActorDamagedEvent.DamageSource.SKILL) == requireSkillDamage &&
                (type.equals("both", true) || type.equals("projectile", true) == projectile)
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorDamagedEvent, data: MutableMap<String, Any>) {
        data["api-taken"] = event.amount
        data["api-dealt"] = event.amount
    }

    internal fun isUsingTarget(settings: Data): Boolean {
        return settings.getString("target", "true").equals("false", true)
    }
}