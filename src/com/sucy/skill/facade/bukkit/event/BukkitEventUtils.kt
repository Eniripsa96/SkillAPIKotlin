package com.sucy.skill.facade.bukkit.event

import com.sucy.skill.data.PluginState
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.api.event.actor.DamageSource
import org.bukkit.entity.Entity
import org.bukkit.entity.Projectile

/**
 * SkillAPIKotlin © 2018
 */
object BukkitEventUtils {

    fun determineSource(actor: Entity): DamageSource {
        return when {
            PluginState.skillDamage -> DamageSource.SKILL
            actor is Projectile -> DamageSource.PROJECTILE
            else -> DamageSource.ATTACK
        }
    }
}