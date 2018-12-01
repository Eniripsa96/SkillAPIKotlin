package com.sucy.skill.util.math.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import com.sucy.skill.util.math.MathConst
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Sin : Func {
    override val token = "sin"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(Math.sin(stack.pop() * MathConst.DEG_TO_RAD))
    }
}