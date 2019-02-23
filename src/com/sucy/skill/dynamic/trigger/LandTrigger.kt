package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorLandedEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class LandTrigger : Trigger<ActorLandedEvent>() {

    /** {@inheritDoc}  */
    override val key: String
        get() = "LAND"

    /** {@inheritDoc}  */
    override val event: Class<ActorLandedEvent>
        get() = ActorLandedEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorLandedEvent, level: Int): Boolean {
        val minDistance = metadata.getDouble("min-distance", 0.0)
        return event.distance >= minDistance
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorLandedEvent, data: MutableMap<String, Any>) {}

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorLandedEvent): Actor? {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorLandedEvent, settings: Data): Actor? {
        return event.actor
    }
}
