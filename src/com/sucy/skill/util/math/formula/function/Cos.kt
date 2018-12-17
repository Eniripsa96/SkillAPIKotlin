package com.sucy.skill.util.math.formula.function

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import com.sucy.skill.util.math.formula.MathConst
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Cos : Func {
    override val token = "cos"
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(Math.cos(stack.pop() * MathConst.DEG_TO_RAD))
    }
}