package com.sucy.skill.util.math.shape

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.math.random

data class SphereShape(val radius: DynamicFormula) : Shape {
    override val isFinite = false
    override fun getPositionSequence(level: Int, caster: Actor, target: Actor): Sequence<Vector3> {
        val radius = radius.evaluate(caster, target, level.toDouble())
        val diameter = radius * 2

        return generateSequence { generatePoint(radius, diameter) }
    }

    private fun generatePoint(radius: Double, diameter: Double): Vector3 {
        val point = Vector3()
        do point.apply {
            x = random() * diameter - radius
            y = random() * diameter - radius
            z = random() * diameter - radius
        } while (point.lengthSq > radius)
        return point
    }
}