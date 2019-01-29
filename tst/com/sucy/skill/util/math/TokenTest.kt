package com.sucy.skill.util.math

import com.sucy.skill.util.math.formula.Token
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
open class TokenTest {
    fun test(token: Token, vararg numbers: Double): Double {
        val stack = Stack<Double>()
        numbers.forEach { stack.add(it) }

        token.apply(stack, DoubleArray(0))
        return stack.pop()
    }
}