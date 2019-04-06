package com.sucy.skill.facade.api.entity

import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.data.Vector3

/**
 * SkillAPIKotlin © 2018
 */
interface Entity {
    val location: Location
    val velocity: Vector3
    val type: String
    val name: String

    fun setOnFire(duration: Long)
    fun clearFire()
}