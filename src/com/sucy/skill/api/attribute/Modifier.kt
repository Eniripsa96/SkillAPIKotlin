package com.sucy.skill.api.attribute

import com.sucy.skill.dynamic.Effect
import com.sucy.skill.util.math.formula.Formula

data class Modifier(
        val formula: Formula,
        val conditions: Map<String, String>
) {
    fun appliesTo(effect: Effect): Boolean {
        return conditions.none {
            !effect.metadata.getString(it.key).equals(it.value, ignoreCase = true)
        }
    }
}