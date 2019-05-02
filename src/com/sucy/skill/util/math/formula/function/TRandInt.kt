package com.sucy.skill.util.math.formula.function

import java.util.*
import kotlin.math.floor

object TRandInt : Func {
    override val token = "trandInt"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        stack.push(floor(value * (Math.random() + Math.random()) / 2))
    }
}