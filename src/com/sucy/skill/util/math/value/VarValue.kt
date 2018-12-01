package com.sucy.skill.util.math.value

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access

/**
 * SkillAPIKotlin © 2018
 */
data class VarValue(private val key: String) : Value {
    override fun getValue(access: Access, context: Actor?): Double {
        return try {
            (access.find(key, context))?.toString()?.toDouble() ?: 0.0
        } catch (ex: Exception) {
            0.0
        }
    }
}