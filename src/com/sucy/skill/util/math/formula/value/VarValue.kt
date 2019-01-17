package com.sucy.skill.util.math.formula.value

/**
 * SkillAPIKotlin © 2018
 */
data class VarValue(private val index: Int) : Value {
    override fun getValue(values: DoubleArray): Double {
        return values[index]
    }
}