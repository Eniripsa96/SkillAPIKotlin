package com.sucy.skill.facade.bukkit.entity

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

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
}