package com.sucy.skill.facade.bukkit.event.proxy.actor

import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.actor.ActorMoveEvent
import com.sucy.skill.facade.bukkit.data.BukkitLocation
import com.sucy.skill.facade.bukkit.toActor
import com.sucy.skill.facade.bukkit.toBukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerMoveEvent

object BukkitPlayerMoveEventProxy : EventProxy<ActorMoveEvent, Event, PlayerMoveEvent> {
    override val targetType = PlayerMoveEvent::class.java

    override fun proxy(event: PlayerMoveEvent): ActorMoveEvent {
        return ActorMoveEvent(
                actor = event.player.toActor()!!,
                from = BukkitLocation(event.from),
                to = BukkitLocation(event.to),
                cancelled = event.isCancelled
        )
    }

    override fun proxy(event: ActorMoveEvent): PlayerMoveEvent {
        val result = PlayerMoveEvent(
                event.actor.toBukkit() as Player,
                event.from.toBukkit(),
                event.to.toBukkit()
        )
        result.isCancelled = event.cancelled
        return result
    }

    override fun appliesTo(event: PlayerMoveEvent): Boolean = true
    override fun appliesTo(event: ActorMoveEvent): Boolean = event.actor is com.sucy.skill.facade.api.entity.Player
}