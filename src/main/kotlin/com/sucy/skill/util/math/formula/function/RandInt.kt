package com.sucy.skill.util.math.formula.function

import java.util.*
import kotlin.math.floor

object RandInt : Func {
    override val token = "randInt"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        stack.push(floor(value * Math.random()))
    }
}