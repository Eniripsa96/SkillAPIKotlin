package com.sucy.skill.facade.bukkit.data

import com.sucy.skill.facade.api.data.Vector3
import org.bukkit.util.Vector

/**
 * SkillAPIKotlin © 2018
 */
data class BukkitVector3(private val vector: Vector) : Vector3 {
    override val x: Double
        get() = vector.x
    override val y: Double
        get() = vector.y
    override val z: Double
        get() = vector.z
}