package com.sucy.skill.util.math.formula

import com.sucy.skill.util.math.formula.value.ConstValue
import com.sucy.skill.util.math.formula.value.VarValue

class DynamicFormula(equation: String) : Formula(equation, ArrayList()) {

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
}