package com.sucy.skill.util.math.formula.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Ceil : Func {
    override val token = "ceil"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(Math.ceil(stack.pop()))
    }
}