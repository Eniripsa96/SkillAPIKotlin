package com.sucy.skill.util.math.shape

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector2
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.math.random

class CircleShape(
        val radius: DynamicFormula,
        val amount: DynamicFormula
) : Shape {
    override fun getPositions(level: Int, caster: Actor, target: Actor): List<Vector3> {
        val amount = amount.evaluate(caster, target, level.toDouble()).toInt()
        val radius = radius.evaluate(caster, target, level.toDouble())
        val diameter = radius * 2

        return List(amount) { generatePoint(radius, diameter) }
    }

    private fun generatePoint(radius: Double, diameter: Double): Vector3 {
        val point = Vector2()
        do point.apply {
            x = random() * diameter - radius
            y = random() * diameter - radius
        } while (point.lengthSq > radius)
        return Vector3(point.x, 0.0, point.y)
    }
}