package com.sucy.skill.facade.bukkit.entity

import com.sucy.skill.facade.api.World
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.EntityType
import com.sucy.skill.facade.api.entity.VanillaEntityType
import com.sucy.skill.facade.bukkit.BukkitWorld
import com.sucy.skill.facade.bukkit.data.BukkitLocation
import com.sucy.skill.facade.bukkit.wrap
import com.sucy.skill.util.math.Vector3
import org.bukkit.Bukkit.getWorld
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

/**
 * SkillAPIKotlin © 2018
 */
open class BukkitEntity(open val entity: Entity) : com.sucy.skill.facade.api.entity.Entity {
    // TODO - do proper mapping and fall back to unknown types
    override val type: EntityType
        get() = VanillaEntityType.valueOf(entity.type.name)
    override val world: World
        get() = BukkitWorld(entity.world)
    override var fireTicks: Long
        get() = entity.fireTicks.toLong()
        set(value) {
            entity.fireTicks = value.toInt()
        }

    override var location: Location
        get() = BukkitLocation(entity.location)
        set(it) {
            val coords = it.coords
            entity.teleport(org.bukkit.Location(
                    getWorld(it.world),
                    coords.x,
                    coords.y,
                    coords.z
            ))
        }

    override var velocity: Vector3
        get() = entity.velocity.wrap()
        set(it) {
            entity.velocity = Vector(it.x, it.y, it.z)
        }

    override var name: String
        get() = entity.customName ?: entity.name
        set(it) {
            entity.customName = it
        }

    override fun isOnGround(): Boolean {
        return entity.isOnGround
    }
}