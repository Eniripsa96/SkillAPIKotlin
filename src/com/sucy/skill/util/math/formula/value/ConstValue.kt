package com.sucy.skill.util.math.formula.value

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access

/**
 * SkillAPIKotlin © 2018
 */
data class ConstValue(private val value: Double) : Value {
    override fun getValue(access: Access, context: Actor?): Double {
        return value
    }
}