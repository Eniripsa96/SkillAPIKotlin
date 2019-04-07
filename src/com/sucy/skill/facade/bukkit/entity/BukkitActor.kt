package com.sucy.skill.facade.bukkit.entity

import com.sucy.skill.facade.api.entity.Actor
import org.bukkit.Bukkit.dispatchCommand
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
open class BukkitActor(override val entity: LivingEntity) : BukkitEntity(entity), Actor {
    override val exists: Boolean
        get() = entity.isValid
    override val dead: Boolean
        get() = entity.isDead
    override val uuid: UUID
        get() = entity.uniqueId
    override var health: Double
        get() = entity.health
        set(value) { entity.health = value }
    override val maxHealth: Double
        get() = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).value
    override val level = 1
    
    override fun hasPermission(permission: String): Boolean {
        return entity.hasPermission(permission)
    }

    override fun executeCommand(command: String) {
        val wasOp = entity.isOp
        entity.isOp = true
        dispatchCommand(entity, command)
        entity.isOp = wasOp
    }
}