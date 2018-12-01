package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorMoveEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class MoveTrigger : Trigger<ActorMoveEvent> {

    /** {@inheritDoc}  */
    override val key: String
        get() = "MOVE"

    /** {@inheritDoc}  */
    override val event: Class<ActorMoveEvent>
        get() = ActorMoveEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorMoveEvent, level: Int, settings: Data): Boolean {
        return event.from.world == event.to.world
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorMoveEvent, data: MutableMap<String, Any>) {}

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorMoveEvent): Actor? {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorMoveEvent, settings: Data): Actor? {
        return event.actor
    }
}
