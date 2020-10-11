package com.sucy.skill.facade.api.entity

import com.sucy.skill.facade.api.World
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.util.math.Vector3

/**
 * SkillAPIKotlin © 2018
 */
interface Entity {
    var location: Location
    var velocity: Vector3
    val type: EntityType
    var name: String
    val world: World
    var fireTicks: Long

    fun clearFire() {
        fireTicks = 0
    }

    fun isOnGround(): Boolean

    fun moveTo(
        x: Double,
        y: Double,
        z: Double,
        world: String = location.world,
        yaw: Double = location.yaw,
        pitch: Double = location.pitch
    )
}