package com.sucy.skill.facade.bukkit.event

import com.sucy.skill.data.PluginState
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import org.bukkit.entity.Entity
import org.bukkit.entity.Projectile

/**
 * SkillAPIKotlin © 2018
 */
object BukkitEventUtils {

    fun determineSource(actor: Entity): ActorDamagedByActorEvent.DamageSource {
        return when {
            PluginState.skillDamage -> ActorDamagedByActorEvent.DamageSource.SKILL
            actor is Projectile -> ActorDamagedByActorEvent.DamageSource.PROJECTILE
            else -> ActorDamagedByActorEvent.DamageSource.ATTACK
        }
    }
}