package com.sucy.skill.util.math.formula.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Log : Func {
    override val token = "log"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(Math.log(Math.max(1.0, stack.pop())))
    }
}