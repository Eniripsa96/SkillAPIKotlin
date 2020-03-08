package com.sucy.skill.facade.api.managers

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.entity.Player
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface PlayerManager {
    val onlinePlayers: List<Player>
    fun getPlayer(uuid: UUID): Player?

    fun getPlayer(name: String): Player? {
        val uuid = SkillAPI.entityData.playerIds.reversed()[name]
        return if (uuid == null) null else getPlayer(uuid)
    }

    fun isPlayerOnline(uuid: UUID): Boolean {
        return getPlayer(uuid) != null
    }

    fun isPlayerOnline(name: String): Boolean {
        return getPlayer(name) != null
    }
}