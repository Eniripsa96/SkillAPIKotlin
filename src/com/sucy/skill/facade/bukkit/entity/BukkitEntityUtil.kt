package com.sucy.skill.facade.bukkit.entity

import com.sucy.skill.facade.api.entity.Actor
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile

/**
 * SkillAPIKotlin © 2018
 */
object BukkitEntityUtil {
    fun wrap(entity: Entity): com.sucy.skill.facade.api.entity.Entity {
        return when (entity) {
            is Player -> BukkitPlayer(entity)
            is LivingEntity -> BukkitActor(entity)
            else -> BukkitEntity(entity)
        }
    }

    fun findActor(entity: Entity): Actor? {
        return when (entity) {
            is Projectile -> entity.shooter?.takeIf { it is LivingEntity }?.let { BukkitActor(it as LivingEntity) }
            is Player -> BukkitPlayer(entity)
            is LivingEntity -> BukkitActor(entity)
            else -> null
        }
    }

    fun toBukkit(entity: com.sucy.skill.facade.api.entity.Entity): Entity {
        return (entity as BukkitEntity).entity
    }
}