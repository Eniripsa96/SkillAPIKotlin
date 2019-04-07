package com.sucy.skill.facade.api.data

/**
 * SkillAPIKotlin © 2018
 */
interface Location {
    val world: String
    val coords: Vector3
    val yaw: Double
    val pitch: Double
    val forward: Vector3
}