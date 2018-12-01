package com.sucy.skill.util.math.operator

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Pow : Operator {
    override val token = '^'
    override val precedence = 3
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        val right = stack.pop()
        stack.push(Math.pow(stack.pop(), right))
    }
}