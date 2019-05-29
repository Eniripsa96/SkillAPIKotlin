package com.sucy.skill.util.math

import kotlin.math.max
import kotlin.math.min

fun sq(num: Double) = num * num
fun sq(num: Int) = num * num
fun limit(num: Double, lower: Double, upper: Double) = max(lower, min(upper, num))