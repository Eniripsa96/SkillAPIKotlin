package com.sucy.skill.util.math.operator

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Plus : Operator {
    override val token = '+'
    override val precedence = 1
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(stack.pop() + stack.pop())
    }
}