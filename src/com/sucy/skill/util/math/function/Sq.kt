package com.sucy.skill.util.math.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Sq : Func {
    override val token = "sq"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        val value = stack.pop()
        stack.push(value * value)
    }
}