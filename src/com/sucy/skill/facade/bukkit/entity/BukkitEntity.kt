package com.sucy.skill.facade.bukkit.entity

import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.data.Vector3
import com.sucy.skill.facade.bukkit.data.BukkitLocation
import com.sucy.skill.facade.bukkit.data.BukkitVector3
import org.bukkit.entity.Entity

/**
 * SkillAPIKotlin © 2018
 */
open class BukkitEntity(open val entity: Entity) : com.sucy.skill.facade.api.entity.Entity {
    override val type: String
        get() = entity.type.name
    override val location: Location
        get() = BukkitLocation(entity.location)
    override val velocity: Vector3
        get() = BukkitVector3(entity.velocity)
    override val name: String
        get() = entity.customName ?: entity.name
}