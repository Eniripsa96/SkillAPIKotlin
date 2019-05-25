package com.sucy.skill.facade.bukkit.event.proxy.actor

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent
import com.sucy.skill.facade.bukkit.data.BukkitItem
import com.sucy.skill.facade.bukkit.skillAPI
import com.sucy.skill.facade.bukkit.toBukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDeathEvent

object BukkitActorDeathEventProxy : EventProxy<ActorDeathEvent, Event, EntityDeathEvent> {
    override val targetType = EntityDeathEvent::class.java

    override fun proxy(event: EntityDeathEvent): ActorDeathEvent {
        return ActorDeathEvent(
                actor = event.entity.skillAPI() as Actor,
                killer = event.entity.killer?.skillAPI() as Actor?,
                drops = event.drops.map { BukkitItem(it) },
                exp = event.droppedExp
        )
    }

    override fun proxy(event: ActorDeathEvent): EntityDeathEvent {
        return EntityDeathEvent(
                event.actor.toBukkit() as LivingEntity,
                event.drops.map { it.toBukkit() },
                event.exp
        )
    }

    override fun appliesTo(event: EntityDeathEvent): Boolean = true
}