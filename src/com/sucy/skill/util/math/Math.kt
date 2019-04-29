package com.sucy.skill.util.math

import kotlin.math.max

fun sq(num: Double) = num * num
fun sq(num: Int) = num * num
fun limit(num: Double, lower: Double, upper: Double) = max(lower, max(upper, num))