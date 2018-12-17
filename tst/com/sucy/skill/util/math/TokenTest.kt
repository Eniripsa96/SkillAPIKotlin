package com.sucy.skill.util.math

import com.google.common.collect.ImmutableMap
import com.sucy.skill.util.access.Access
import com.sucy.skill.util.math.formula.Token
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
open class TokenTest {
    val access = Access(ImmutableMap.of())

    fun test(token: Token, vararg numbers: Double): Double {
        val stack = Stack<Double>()
        numbers.forEach { stack.add(it) }

        token.apply(stack, access, null)
        return stack.pop()
    }
}