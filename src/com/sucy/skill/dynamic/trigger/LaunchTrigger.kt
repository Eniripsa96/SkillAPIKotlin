package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ProjectileLaunchEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class LaunchTrigger : Trigger<ProjectileLaunchEvent>() {

    /** {@inheritDoc}  */
    override val key: String
        get() = "LAUNCH"

    /** {@inheritDoc}  */
    override val event: Class<ProjectileLaunchEvent>
        get() = ProjectileLaunchEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ProjectileLaunchEvent, level: Int): Boolean {
        val type = metadata.getString("type", "any")
        return type.equals("ANY", true) || type.equals(event.projectile.type, true)
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ProjectileLaunchEvent, data: MutableMap<String, Any>) {}

    /** {@inheritDoc}  */
    override fun getCaster(event: ProjectileLaunchEvent): Actor? {
        return event.projectile.shooter
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ProjectileLaunchEvent, settings: Data): Actor? {
        return event.projectile.shooter
    }
}
