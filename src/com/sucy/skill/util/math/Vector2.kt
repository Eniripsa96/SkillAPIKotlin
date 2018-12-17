package com.sucy.skill.util.math

/**
 * SkillAPIKotlin © 2018
 */
data class Vector2(var x: Double = 0.0, var y: Double = 0.0) {

    fun rotate(cos: Double, sin: Double): Vector2 {
        val newX = x * cos - y * sin
        y = x * sin + y * cos
        x = newX
        return this
    }
}