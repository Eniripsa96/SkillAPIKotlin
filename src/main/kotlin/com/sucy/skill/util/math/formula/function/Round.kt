package com.sucy.skill.util.math.formula.function

import java.util.*
import kotlin.math.round

object Round : Func {
    override val token = "round"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        stack.push(round(value))
    }
}