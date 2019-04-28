package com.sucy.skill.facade.bukkit.event.actor

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.DefaultEventProxy
import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.bukkit.BukkitUtil
import com.sucy.skill.facade.bukkit.event.BukkitEventUtils
import com.sucy.skill.facade.bukkit.skillAPI
import com.sucy.skill.util.match
import com.sucy.skill.util.text.enumName
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

/**
 * SkillAPIKotlin © 2018
 */
object BukkitActorDamagedByActorEventProxy : EventProxy<ActorDamagedByActorEvent, Event, EntityDamageByEntityEvent> {
    override val targetType = EntityDamageByEntityEvent::class.java

    override fun proxy(event: EntityDamageByEntityEvent): ActorDamagedByActorEvent {
        return ActorDamagedByActorEvent(
                actor = event.entity.skillAPI() as Actor,
                source = BukkitEventUtils.determineSource(event.damager),
                damageType = event.cause.name,
                attacker = BukkitUtil.findActor(event.damager)!!,
                amount = event.damage,
                cancelled = event.isCancelled
        )
    }

    override fun proxy(event: ActorDamagedByActorEvent): EntityDamageByEntityEvent {
        return EntityDamageByEntityEvent(
                BukkitUtil.toBukkit(event.actor),
                BukkitUtil.toBukkit(event.attacker),
                EntityDamageEvent.DamageCause::class.match(event.damageType, EntityDamageEvent.DamageCause.CUSTOM),
                event.amount
        )
    }

    override fun appliesTo(event: EntityDamageByEntityEvent): Boolean {
        return event.entity is LivingEntity && BukkitUtil.findActor(event.damager) != null
    }
}