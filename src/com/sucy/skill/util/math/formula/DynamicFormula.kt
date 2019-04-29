package com.sucy.skill.util.math.formula

import com.google.common.collect.ImmutableMap
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.value.ConstValue
import com.sucy.skill.util.math.formula.value.VarValue

class DynamicFormula(equation: String) : Formula(equation, ArrayList()) {

    fun evaluate(caster: Actor, target: Actor = caster): Double {
        formulaCaster = caster
        formulaTarget = target
        val values: DoubleArray = keys.map { formulaGetter(it) }.toDoubleArray()
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

        private val formulaGetter: (String) -> Double = {
            if (it.startsWith("target.")) getValue(formulaTarget!!, it.substring(7))
            else getValue(formulaCaster!!, it)
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