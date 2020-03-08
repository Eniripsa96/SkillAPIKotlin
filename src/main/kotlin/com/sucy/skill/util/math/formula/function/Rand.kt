package com.sucy.skill.util.math.formula.function

import java.util.*

object Rand : Func {
    override val token = "rand"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        stack.push(value * Math.random())
    }
}