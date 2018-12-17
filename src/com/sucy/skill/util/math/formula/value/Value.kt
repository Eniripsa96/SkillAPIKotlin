package com.sucy.skill.util.math.formula.value

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import com.sucy.skill.util.math.formula.Token
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface Value : Token {
    fun getValue(access: Access, context: Actor?): Double
    override fun apply(stack: Stack<Double>, access: Access, context: Actor?) {
        stack.push(getValue(access, context))
    }
}