package com.sucy.skill.util.math.formula

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface Token {
    fun apply(stack: Stack<Double>, values: DoubleArray)
}