package com.sucy.skill.util.math

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import com.sucy.skill.util.math.function.Func
import com.sucy.skill.util.math.function.Funcs
import com.sucy.skill.util.math.operator.Operator
import com.sucy.skill.util.math.operator.Operators
import com.sucy.skill.util.math.operator.Parenthesis
import com.sucy.skill.util.math.value.ConstValue
import com.sucy.skill.util.math.value.VarValue
import java.util.*
import java.util.stream.Collectors

/**
 * SkillAPIKotlin © 2018
 */
class Formula(equation: String, private val access: Access) {
    val tokens = ArrayList<Token>()

    fun evaluate(context: Actor?): Double {
        val stack = Stack<Double>()
        tokens.forEach { it.apply(stack, access, context) }
        return stack.pop()
    }

    init {
        var start = 0
        val operators = Stack<OrderedToken>()
        for (i in 0 until equation.length) {
            if (OP_TOKENS.containsKey(equation[i]) || equation[i] == '(' || equation[i] == ')') {
                val token = equation.substring(start, i).trim()
                if (FUNC_TOKENS.containsKey(token)) {
                    if (equation[i] != '(') {
                        throw IllegalArgumentException("Functions must be immediately followed by a (")
                    } else {
                        operators.push(FUNC_TOKENS[token])
                    }
                } else if (start < i) {
                    parseVal(token)
                }
                start = i + 1

                when {
                    equation[i] == '(' -> operators.push(Parenthesis)
                    equation[i] == ')' -> {
                        while (operators.peek() != Parenthesis) {
                            tokens.add(operators.pop())
                        }
                        operators.pop()
                        if (!operators.isEmpty() && operators.peek() is Func) {
                            tokens.add(operators.pop())
                        }
                    }
                    else -> {
                        val newOp = OP_TOKENS[equation[i]]!!
                        while (!operators.isEmpty() && operators.peek().precedence >= newOp.precedence) {
                            tokens.add(operators.pop())
                        }
                        operators.push(newOp)
                    }
                }
            }
        }
        parseVal(equation.substring(start, equation.length))
        while (!operators.isEmpty()) {
            tokens.add(operators.pop())
        }
    }

    private fun parseVal(token: String) {
        if (token.isBlank()) return

        try {
            tokens.add(ConstValue(token.toDouble()))
        } catch (ex: NumberFormatException) {
            tokens.add(VarValue(token))
        }
    }

    companion object {
        private val OP_TOKENS: Map<Char, Operator> = Arrays.stream(Operators.values())
                .collect(Collectors.toMap({ op: Operators -> op.token.token }) { op: Operators -> op.token })

        private val FUNC_TOKENS: Map<String, Func> = Arrays.stream(Funcs.values())
                .collect(Collectors.toMap({ func: Funcs -> func.func.token }) { func: Funcs -> func.func })
    }
}