package com.sucy.skill.util.math

import kotlin.math.PI

private const val DEGREES_TO_RADIANS = PI / 180
private const val RADIANS_TO_DEGREES = 180 / PI

/**
 * Assumes [this] is a time measured in seconds and converts [this]
 * to ticks (1/20 of a second)
 */
fun Double.toTicks(): Long {
    return (this * 20).toLong()
}

/**
 * Assumes [this] is a time measured in seconds and converts [this]
 * to milliseconds (1/1000 of a second)
 */
fun Double.toMillis(): Long {
    return (this * 1000).toLong()
}

/**
 * Converts a coordinate into a chunk coordinate
 */
fun Double.toChunk(): Int {
    return this.toInt() shr 4
}

fun Double.toRadians(): Double {
    return this * DEGREES_TO_RADIANS
}

fun Double.toDegrees(): Double {
    return this * RADIANS_TO_DEGREES
}
