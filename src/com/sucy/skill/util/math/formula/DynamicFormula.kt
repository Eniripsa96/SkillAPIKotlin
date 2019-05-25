package com.sucy.skill.util.math.formula

import com.google.common.collect.ImmutableMap
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.value.ConstValue
import com.sucy.skill.util.math.formula.value.VarValue

class DynamicFormula(expression: String) : Formula(expression, ArrayList()) {

    fun evaluate(caster: Actor, target: Actor = caster, x: Double): Double = evaluate(caster, target, "x" to x)

    fun evaluate(caster: Actor, target: Actor = caster, vararg extras: Pair<String, Double>): Double {
        formulaCaster = caster
        formulaTarget = target
        val values: DoubleArray = keys.map { formulaGetter(it, extras.toMap()) }.toDoubleArray()
        return evaluate(*values)
    }

    fun evaluate(valueGetter: (String) -> Double): Double {
        val values: DoubleArray = keys.map(valueGetter).toDoubleArray()
        return evaluate(*values)
    }

    override fun parseVal(token: String) {
        if (token.isBlank()) return

        try {
            tokens.add(ConstValue(token.toDouble()))
        } catch (ex: NumberFormatException) {
            val index = keys.indexOf(token.trim())
            if (index < 0) {
                tokens.add(VarValue(keys.size))
                keys.add(token.trim())
            } else {
                tokens.add(VarValue(index))
            }
        }
    }

    private companion object {
        private var formulaCaster: Actor? = null
        private var formulaTarget: Actor? = null

        private val formulaGetter: (String, Map<String, Double>) -> Double = { it, extras ->
            when {
                extras.containsKey(it) -> extras.getValue(it)
                it.startsWith("target.") -> getValue(formulaTarget!!, it.substring(7))
                else -> getValue(formulaCaster!!, it)
            }
        }

        fun getValue(actor: Actor, key: String): Double {
            val common = VALUE_KEYS[key]
            return common?.invoke(actor) ?: actor.values[key].total
        }

        private val VALUE_KEYS = ImmutableMap.builder<String, (Actor) -> Double>()
                .put("health") { it.health }
                .put("maxHealth") { it.maxHealth }
                .put("mana") { it.mana }
                .put("maxMana") { it.maxMana }
                .put("lvl") { it.level.toDouble() }
                .put("level") { it.level.toDouble() }
                .build()
    }
}