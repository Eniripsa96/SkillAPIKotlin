package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.PlayerToggleCrouchEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class CrouchTrigger : Trigger<PlayerToggleCrouchEvent> {

    /** {@inheritDoc}  */
    override val key: String
        get() = "CROUCH"

    /** {@inheritDoc}  */
    override val event: Class<PlayerToggleCrouchEvent>
        get() = PlayerToggleCrouchEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: PlayerToggleCrouchEvent, level: Int, settings: Data): Boolean {
        val type = settings.getString("type", "start crouching")
        return type.equals("both", ignoreCase = true) || event.isCrouching != type.equals("stop crouching", ignoreCase = true)
    }

    /** {@inheritDoc}  */
    override fun setValues(event: PlayerToggleCrouchEvent, data: MutableMap<String, Any>) {}

    /** {@inheritDoc}  */
    override fun getCaster(event: PlayerToggleCrouchEvent): Actor {
        return event.player
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: PlayerToggleCrouchEvent, settings: Data): Actor {
        return event.player
    }
}
