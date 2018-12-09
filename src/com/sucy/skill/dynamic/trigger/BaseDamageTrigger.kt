package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
abstract class BaseDamageTrigger : Trigger<ActorDamagedByActorEvent> {
    abstract val requireSkillDamage: Boolean

    /** {@inheritDoc}  */
    override val event: Class<ActorDamagedByActorEvent>
        get() = ActorDamagedByActorEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDamagedByActorEvent, level: Int, settings: Data): Boolean {
        val type = settings.getString("type", "both")
        val min = settings.getDouble("dmg-min")
        val max = settings.getDouble("dmg-max")
        val projectile = event.source == ActorDamagedByActorEvent.DamageSource.PROJECTILE
        return event.amount in min..max &&
                (event.source == ActorDamagedByActorEvent.DamageSource.SKILL) == requireSkillDamage &&
                (type.equals("both", true) || type.equals("projectile", true) == projectile)
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorDamagedByActorEvent, data: MutableMap<String, Any>) {
        data["api-taken"] = event.amount
        data["api-dealt"] = event.amount
    }

    internal fun isUsingTarget(settings: Data): Boolean {
        return settings.getString("target", "true").equals("false", true)
    }
}