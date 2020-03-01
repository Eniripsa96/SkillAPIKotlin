package com.sucy.skill.util.math

/**
 * SkillAPIKotlin © 2018
 */
data class Vector2(var x: Double = 0.0, var y: Double = 0.0) {

    val lengthSq: Double
        get() = x * x + y * y

    fun rotate(cos: Double, sin: Double): Vector2 = Vector2(x * cos - y * sin, x * sin + y * cos)
}