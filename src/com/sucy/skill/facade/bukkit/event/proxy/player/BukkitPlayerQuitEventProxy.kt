package com.sucy.skill.facade.bukkit.event.proxy.player

import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.player.PlayerQuitEvent
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import org.bukkit.event.Event

object BukkitPlayerQuitEventProxy : EventProxy<PlayerQuitEvent, Event, org.bukkit.event.player.PlayerQuitEvent> {
    override val targetType = org.bukkit.event.player.PlayerQuitEvent::class.java

    override fun proxy(event: org.bukkit.event.player.PlayerQuitEvent): PlayerQuitEvent {
        return PlayerQuitEvent(BukkitPlayer(event.player))
    }

    override fun proxy(event: PlayerQuitEvent): org.bukkit.event.player.PlayerQuitEvent {
        throw UnsupportedOperationException("Cannot fake a quit event")
    }

    override fun appliesTo(event: org.bukkit.event.player.PlayerQuitEvent): Boolean = true
}