package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
data class ToggleCrouchEvent(
        val actor: Actor,
        val isCrouching: Boolean
) : Event