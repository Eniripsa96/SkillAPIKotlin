package com.sucy.skill.util.math

/**
 * SkillAPIKotlin © 2018
 */
data class Vector3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
    fun dSq(vector: Vector3): Double {
        return sq(x - vector.x) + sq(y - vector.y) + sq(z - vector.z)
    }
}