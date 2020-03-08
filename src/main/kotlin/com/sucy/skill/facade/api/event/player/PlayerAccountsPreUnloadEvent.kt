package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Player

/**
 * Event called when a player's SkillAPI data is about to be unloaded
 */
data class PlayerAccountsPreUnloadEvent(val player: Player): Event