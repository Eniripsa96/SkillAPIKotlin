package com.sucy.skill.facade.bukkit.event.proxy.player

import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.player.PlayerJoinEvent
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import org.bukkit.event.Event

object BukkitPlayerJoinEventProxy : EventProxy<PlayerJoinEvent, Event, org.bukkit.event.player.PlayerJoinEvent> {
    override val targetType = org.bukkit.event.player.PlayerJoinEvent::class.java

    override fun proxy(event: org.bukkit.event.player.PlayerJoinEvent): PlayerJoinEvent {
        return PlayerJoinEvent(BukkitPlayer(event.player))
    }

    override fun proxy(event: PlayerJoinEvent): org.bukkit.event.player.PlayerJoinEvent {
        throw UnsupportedOperationException("Cannot fake a join event")
    }

    override fun appliesTo(event: org.bukkit.event.player.PlayerJoinEvent): Boolean = true
}