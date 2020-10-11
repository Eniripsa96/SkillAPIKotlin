package com.sucy.skill.util.math

import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private val RANDOM = Random()

fun sq(num: Double) = num * num
fun sq(num: Int) = num * num
fun limit(num: Double, lower: Double, upper: Double) = max(lower, min(upper, num))
fun random() = RANDOM.nextDouble()
fun minAbs(a: Double, b: Double) = if (abs(a) < abs(b)) a else b
fun maxAbs(a: Double, b: Double) = if (abs(a) > abs(b)) a else b