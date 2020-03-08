package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent

/**
 * SkillAPI © 2018
 */
class DeathTrigger : Trigger<ActorDeathEvent>() {

    /** {@inheritDoc}  */
    override val key: String
        get() = "DEATH"

    /** {@inheritDoc}  */
    override val event: Class<ActorDeathEvent>
        get() = ActorDeathEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDeathEvent, level: Int): Boolean {
        return true
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDeathEvent): Actor {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDeathEvent): Actor {
        return event.killer ?: event.actor
    }
}
