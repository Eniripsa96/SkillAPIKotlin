package com.sucy.skill.util.math.formula.value

import com.sucy.skill.util.math.formula.Token
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface Value : Token {
    fun getValue(values: DoubleArray): Double
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(getValue(values))
    }
}