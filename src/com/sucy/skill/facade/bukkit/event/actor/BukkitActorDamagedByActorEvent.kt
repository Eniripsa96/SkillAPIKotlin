package com.sucy.skill.facade.bukkit.event.actor

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.DefaultEventProxy
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.bukkit.BukkitUtil
import com.sucy.skill.facade.bukkit.event.BukkitEventUtils
import com.sucy.skill.facade.bukkit.skillAPI
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

/**
 * SkillAPIKotlin © 2018
 */
data class BukkitActorDamagedByActorEvent(
        override val actor: Actor,
        override val source: ActorDamagedByActorEvent.DamageSource,
        override val damageType: String,
        override val attacker: Actor,
        override var amount: Double,
        override var cancelled: Boolean = false
) : ActorDamagedByActorEvent {

    /**
     * Constructs the event from a [EntityDamageByEntityEvent], throwing
     * an exception if the event is not compatible (not an actor being damaged)
     */
    constructor(event: EntityDamageByEntityEvent) : this(
            actor = event.entity.skillAPI() as Actor,
            source = BukkitEventUtils.determineSource(event.damager),
            damageType = event.cause.name,
            attacker = BukkitUtil.findActor(event.damager)!!,
            amount = event.damage,
            cancelled = event.isCancelled
    )

    fun restore(): EntityDamageByEntityEvent {
        return EntityDamageByEntityEvent(
                BukkitUtil.toBukkit(actor),
                BukkitUtil.toBukkit(attacker),
                EntityDamageEvent.DamageCause.valueOf(damageType),
                amount
        )
    }

    companion object {
        val PROXY = DefaultEventProxy<ActorDamagedByActorEvent, Event, EntityDamageByEntityEvent>(
                EntityDamageByEntityEvent::class.java,
                { BukkitActorDamagedByActorEvent(it) },
                { (it as BukkitActorDamagedByActorEvent).restore() },
                { it.entity is LivingEntity && BukkitUtil.findActor(it.damager) != null }
        )
    }
}