package com.sucy.skill.util.math.formula

import com.sucy.skill.util.math.formula.function.Func
import com.sucy.skill.util.math.formula.function.Funcs
import com.sucy.skill.util.math.formula.operator.Operator
import com.sucy.skill.util.math.formula.operator.Operators
import com.sucy.skill.util.math.formula.operator.Parenthesis
import com.sucy.skill.util.math.formula.value.ConstValue
import com.sucy.skill.util.math.formula.value.VarValue
import java.util.*
import java.util.stream.Collectors

/**
 * SkillAPIKotlin © 2018
 */
open class Formula(val expression: String, protected val keys: MutableList<String>) {
    val tokens = ArrayList<Token>()

    fun evaluate(vararg values: Double): Double {
        if (values.size != keys.size) {
            throw java.lang.IllegalArgumentException("Evaluation arguments do not match expected keys: $keys")
        }

        val stack = Stack<Double>()
        tokens.forEach { it.apply(stack, values) }
        return stack.pop()
    }

    init {
        var start = 0
        val operators = Stack<OrderedToken>()
        for (i in 0 until expression.length) {
            if (OP_TOKENS.containsKey(expression[i]) || expression[i] == '(' || expression[i] == ')') {
                val token = expression.substring(start, i).trim()
                if (FUNC_TOKENS.containsKey(token)) {
                    if (expression[i] != '(') {
                        throw IllegalArgumentException("Functions must be immediately followed by a (")
                    } else {
                        operators.push(FUNC_TOKENS[token])
                    }
                } else if (start < i) {
                    parseVal(token)
                }
                start = i + 1

                when {
                    expression[i] == '(' -> operators.push(Parenthesis)
                    expression[i] == ')' -> {
                        while (operators.peek() != Parenthesis) {
                            tokens.add(operators.pop())
                        }
                        operators.pop()
                        if (!operators.isEmpty() && operators.peek() is Func) {
                            tokens.add(operators.pop())
                        }
                    }
                    else -> {
                        val newOp = OP_TOKENS.getValue(expression[i])
                        while (!operators.isEmpty() && operators.peek().precedence >= newOp.precedence) {
                            tokens.add(operators.pop())
                        }
                        operators.push(newOp)
                    }
                }
            }
        }
        parseVal(expression.substring(start, expression.length))
        while (!operators.isEmpty()) {
            tokens.add(operators.pop())
        }
    }

    protected open fun parseVal(token: String) {
        if (token.isBlank()) return

        try {
            tokens.add(ConstValue(token.toDouble()))
        } catch (ex: NumberFormatException) {
            val index = keys.indexOf(token.trim())
            if (index < 0) throw IllegalArgumentException("Invalid formula: $token is not a valid token")
            tokens.add(VarValue(index))
        }
    }

    companion object {
        private val OP_TOKENS: Map<Char, Operator> = Arrays.stream(Operators.values())
                .collect(Collectors.toMap({ op: Operators -> op.token.token }) { op: Operators -> op.token })

        private val FUNC_TOKENS: Map<String, Func> = Arrays.stream(Funcs.values())
                .collect(Collectors.toMap({ func: Funcs -> func.func.token }) { func: Funcs -> func.func })

        fun const(value: Double): Formula {
            return Formula(value.toString(), Collections.emptyList())
        }
    }
}