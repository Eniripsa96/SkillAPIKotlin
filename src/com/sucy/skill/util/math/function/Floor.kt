package com.sucy.skill.util.math.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Floor : Func {
    override val token = "floor"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(Math.floor(stack.pop()))
    }
}