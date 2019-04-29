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
    val type: String
    var name: String
    val world: World

    fun setOnFire(duration: Long)
    fun clearFire()
}