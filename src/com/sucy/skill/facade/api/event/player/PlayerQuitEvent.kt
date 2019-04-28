package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Player

/**
 * SkillAPIKotlin © 2018
 */
data class PlayerQuitEvent(
        val player: Player
) : Event