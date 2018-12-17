package com.sucy.skill.util.math.formula.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Sign : Func {
    override val token = "sign"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        val value = stack.pop()
        val result = when {
            value > 0 -> 1.0
            value < 0 -> -1.0
            else -> 0.0
        }
        stack.push(result)
    }
}