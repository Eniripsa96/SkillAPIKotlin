package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Player

/**
 * Event called when a player's SkillAPI data is loaded successfully
 * and the player is online and their attributes/values have been applied.
 */
data class PlayerPostInitializeEvent(val player: Player): Event