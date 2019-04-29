package com.sucy.skill.facade.bukkit.data

import com.sucy.skill.facade.bukkit.wrap
import com.sucy.skill.util.math.Vector3
import org.bukkit.Location

/**
 * SkillAPIKotlin © 2018
 */
data class BukkitLocation(private val location: Location) : com.sucy.skill.facade.api.data.Location {
    override val forward: Vector3
        get() = location.direction.wrap()
    override val world: String
        get() = location.world.name
    override val coords: Vector3
        get() = Vector3(location.x, location.y, location.z)
    override val yaw: Double
        get() = location.yaw.toDouble()
    override val pitch: Double
        get() = location.pitch.toDouble()
}