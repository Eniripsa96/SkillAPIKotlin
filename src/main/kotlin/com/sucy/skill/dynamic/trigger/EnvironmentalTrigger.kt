package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent

/**
 * SkillAPI © 2018
 */
class EnvironmentalTrigger : Trigger<ActorDamagedByActorEvent>() {

    /** {@inheritDoc}  */
    override val key: String
        get() = "ENVIRONMENT_DAMAGE"

    /** {@inheritDoc}  */
    override val event: Class<ActorDamagedByActorEvent>
        get() = ActorDamagedByActorEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDamagedByActorEvent, level: Int): Boolean {
        val type = metadata.getString("type", "any").replace(' ', '_')
        return type.equals("ANY", true) || type.equals(event.damageType, true)
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDamagedByActorEvent): Actor {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDamagedByActorEvent): Actor {
        return event.actor
    }
}
