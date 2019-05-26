package com.sucy.skill.api.attribute

import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.util.math.formula.Formula

data class Attribute(
        val key: String,
        val displayName: String,
        val icon: Item,
        val max: Int,
        val dynamicModifiers: Map<EffectType, Map<String, List<Modifier>>>,
        val statModifiers: Map<String, Formula>
) {
    fun modify(effect: Effect, key: String, value: Double, amount: Int): Double {
        val id = "${effect.key}-$key"
        val applicable = dynamicModifiers[effect.type] ?: return value
        val modifiers = applicable[id] ?: return value

        var result = value
        modifiers.filter { it.appliesTo(effect) }.forEach { result = it.formula.evaluate(value, amount.toDouble()) }
        return result
    }

    fun modify(key: String, value: Double, amount: Int): Double {
        val modifier = statModifiers[key] ?: return value
        return modifier.evaluate(value, amount.toDouble())
    }
}