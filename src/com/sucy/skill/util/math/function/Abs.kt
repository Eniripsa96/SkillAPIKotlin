package com.sucy.skill.util.math.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Abs : Func {
    override val token = "abs"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(Math.abs(stack.pop()))
    }
}