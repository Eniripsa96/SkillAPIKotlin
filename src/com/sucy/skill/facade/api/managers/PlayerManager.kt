package com.sucy.skill.facade.api.managers

import com.sucy.skill.facade.api.entity.Player
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface PlayerManager {
    fun getPlayerName(uuid: UUID): String
    fun getPlayerId(name: String): UUID
    fun getPlayer(uuid: UUID): Player
    fun getPlayer(name: String): Player
    fun isPlayerOnline(uuid: UUID): Boolean
    fun isPlayerOnline(name: String): Boolean
}