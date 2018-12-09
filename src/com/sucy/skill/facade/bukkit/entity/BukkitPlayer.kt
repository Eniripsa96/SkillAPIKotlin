package com.sucy.skill.facade.bukkit.entity

import org.bukkit.entity.Player

/**
 * SkillAPIKotlin © 2018
 */
open class BukkitPlayer(private val entity: Player) : BukkitActor(entity), com.sucy.skill.facade.api.entity.Player {
    override fun getBukkitEntity(): Player {
        return entity
    }
}