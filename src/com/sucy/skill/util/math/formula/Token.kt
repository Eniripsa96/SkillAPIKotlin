package com.sucy.skill.util.math.formula

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface Token {
    fun apply(stack: Stack<Double>, access: Access, context: Actor?)
}