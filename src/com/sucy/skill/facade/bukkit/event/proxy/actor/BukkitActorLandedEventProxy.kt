package com.sucy.skill.facade.bukkit.event.proxy.actor

import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.actor.ActorLandedEvent
import com.sucy.skill.facade.bukkit.entity.BukkitActor
import com.sucy.skill.facade.bukkit.event.actor.BukkitActorLandedEvent
import org.bukkit.event.Event

object BukkitActorLandedEventProxy : EventProxy<ActorLandedEvent, Event, BukkitActorLandedEvent> {
    override val targetType = BukkitActorLandedEvent::class.java

    override fun proxy(event: BukkitActorLandedEvent): ActorLandedEvent {
        return ActorLandedEvent(event.actor, event.distance)
    }

    override fun proxy(event: ActorLandedEvent): BukkitActorLandedEvent {
        return BukkitActorLandedEvent(event.actor as BukkitActor, event.distance)
    }

    override fun appliesTo(event: BukkitActorLandedEvent): Boolean = true
}
