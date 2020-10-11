package com.sucy.skill.util.math

import kotlin.math.abs
import kotlin.math.sqrt

/**
 * SkillAPIKotlin © 2018
 */
data class Vector3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
    val lengthSq: Double
        get() = x * x + y * y + z * z
    val length: Double
        get() = sqrt(lengthSq)

    val normal by lazy {
        val lengthSq = this.lengthSq
        if (lengthSq == 0.0 || lengthSq == 1.0) {
            this
        } else {
            this / sqrt(lengthSq)
        }
    }

    val flatNormal by lazy {
        val vec = copy(y = 0.0)
        val lengthSq = vec.lengthSq
        if (lengthSq == 0.0 || lengthSq == 1.0) {
            vec
        } else {
            vec / sqrt(lengthSq)
        }
    }

    fun abs(): Vector3 = copy(x = abs(x), y = abs(y), z = abs(z))
    fun toInt(): IntVector3 = IntVector3(x.toInt(), y.toInt(), z.toInt())
    fun dSq(vector: Vector3): Double = sq(x - vector.x) + sq(y - vector.y) + sq(z - vector.z)
    fun dot(other: Vector3): Double = x * other.x + y * other.y + z * other.z

    fun cross(other: Vector3): Vector3 = Vector3(
        x = y * other.z - z * other.y,
        y = z * other.x - x * other.z,
        z = x * other.y - y * other.x
    )

    operator fun plus(other: Vector3): Vector3 = Vector3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3): Vector3 = Vector3(x - other.x, y - other.y, z - other.z)
    operator fun times(factor: Vector3): Vector3 = Vector3(x * factor.x, y * factor.y, z * factor.z)
    operator fun times(num: Double): Vector3 = Vector3(x * num, y * num, z * num)
    operator fun div(num: Double): Vector3 = Vector3(x / num, y / num, z / num)

    companion object {
        val ZERO = Vector3()
    }
}