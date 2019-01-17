package com.sucy.skill.util.math.formula.value

/**
 * SkillAPIKotlin © 2018
 */
data class ConstValue(private val value: Double) : Value {
    override fun getValue(values: DoubleArray): Double {
        return value
    }
}