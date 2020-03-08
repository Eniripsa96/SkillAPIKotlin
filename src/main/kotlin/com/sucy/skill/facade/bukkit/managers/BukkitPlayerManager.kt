package com.sucy.skill.facade.bukkit.managers

import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.managers.PlayerManager
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import org.bukkit.Bukkit
import java.util.*

class BukkitPlayerManager : PlayerManager {
    override val onlinePlayers: List<Player>
        get() = Bukkit.getOnlinePlayers().map { BukkitPlayer(it) }

    override fun getPlayer(uuid: UUID): Player? {
        val bukkitPlayer = Bukkit.getPlayer(uuid)
        return if (bukkitPlayer == null) null else BukkitPlayer(bukkitPlayer)
    }
}