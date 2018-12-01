package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.OrderedToken

/**
 * SkillAPIKotlin © 2018
 */
interface Func : OrderedToken {
    val token: String
    override val precedence: Int
        get() = 0
}
