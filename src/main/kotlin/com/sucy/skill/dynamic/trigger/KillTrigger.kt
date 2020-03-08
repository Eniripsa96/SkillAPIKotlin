package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent

/**
 * SkillAPI © 2018
 */
class KillTrigger : Trigger<ActorDeathEvent>() {

    /** {@inheritDoc}  */
    override val key: String
        get() = "KILL"

    /** {@inheritDoc}  */
    override val event: Class<ActorDeathEvent>
        get() = ActorDeathEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDeathEvent, level: Int): Boolean {
        return event.killer != null
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDeathEvent): Actor? {
        return event.killer
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDeathEvent): Actor? {
        return event.killer
    }
}
