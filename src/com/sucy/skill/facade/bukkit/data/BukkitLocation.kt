package com.sucy.skill.facade.bukkit.data

import org.bukkit.Location

/**
 * SkillAPIKotlin © 2018
 */
data class BukkitLocation(private val location: Location) : com.sucy.skill.facade.api.data.Location {
    override val world: String
        get() = location.world.name
    override val coords = BukkitVector3(location.toVector())
    override val yaw: Double
        get() = location.yaw.toDouble()
    override val pitch: Double
        get() = location.pitch.toDouble()
}