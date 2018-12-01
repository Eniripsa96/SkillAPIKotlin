package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Player

/**
 * SkillAPIKotlin © 2018
 */
interface PlayerToggleCrouchEvent : Event {
    val player: Player
    val isCrouching: Boolean
}