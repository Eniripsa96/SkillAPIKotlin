package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDamagedEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class EnvironmentalTrigger : Trigger<ActorDamagedEvent> {

    /** {@inheritDoc}  */
    override val key: String
        get() = "ENVIRONMENT_DAMAGE"

    /** {@inheritDoc}  */
    override val event: Class<ActorDamagedEvent>
        get() = ActorDamagedEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDamagedEvent, level: Int, settings: Data): Boolean {
        val type = settings.getString("type", "any").replace(' ', '_')
        return event.attacker == null && type.equals("ANY", true) || type.equals(event.damageType, true)
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorDamagedEvent, data: MutableMap<String, Any>) {}

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDamagedEvent): Actor {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDamagedEvent, settings: Data): Actor {
        return event.actor
    }
}
