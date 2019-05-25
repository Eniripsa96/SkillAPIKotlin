package com.sucy.skill.facade.bukkit.event.proxy.player

import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.player.AsyncPlayerLoginEvent
import org.bukkit.event.Event
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

object BukkitAsyncPlayerLoginEventProxy : EventProxy<AsyncPlayerLoginEvent, Event, AsyncPlayerPreLoginEvent> {
    override val targetType = AsyncPlayerPreLoginEvent::class.java

    override fun proxy(event: AsyncPlayerPreLoginEvent): AsyncPlayerLoginEvent {
        return AsyncPlayerLoginEvent(event.uniqueId)
    }

    override fun proxy(event: AsyncPlayerLoginEvent): AsyncPlayerPreLoginEvent {
        throw UnsupportedOperationException("Cannot fake an async login event")
    }

    override fun appliesTo(event: AsyncPlayerPreLoginEvent): Boolean {
        TODO("not implemented")
    }

}