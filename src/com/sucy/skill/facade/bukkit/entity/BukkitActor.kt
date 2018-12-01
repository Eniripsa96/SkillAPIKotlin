package com.sucy.skill.facade.bukkit.entity

import com.sucy.skill.facade.api.entity.Actor
import org.bukkit.entity.LivingEntity
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
open class BukkitActor(private val entity: LivingEntity) : BukkitEntity(entity), Actor {
    override val uuid: UUID
        get() = entity.uniqueId
    
    override fun hasPermission(permission: String): Boolean {
        return entity.hasPermission(permission)
    }
}