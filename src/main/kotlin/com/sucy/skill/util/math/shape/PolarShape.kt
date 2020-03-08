package com.sucy.skill.util.math.shape

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector2
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class PolarShape(val formula: DynamicFormula, val steps: Int, val domain: Double) : Shape {
    override val isFinite = true

    private val rotations = computeRotations()

    override fun getPositionSequence(level: Int, caster: Actor, target: Actor): Sequence<Vector3> {
        val domain = domain / max(1, steps)
        return sequence {
            for (i in 0 until steps) {
                val rotation = rotations[i]
                val t = domain * i
                val r = formula.evaluate(caster, target, "t" to t, "p" to i.toDouble() / steps, "c" to rotation.x, "s" to rotation.y)
                yield(Vector3(x = r * rotation.x, z = r * rotation.y))
            }
        }
    }

    private fun computeRotations(): List<Vector2> {
        val steps = max(1, steps)
        val angle = 2 * PI * domain / steps
        val cos = cos(angle)
        val sin = sin(angle)

        var rotation = Vector2(1.0, 0.0)
        return List(steps) {
            val result = rotation
            rotation = rotation.rotate(cos, sin)
            result
        }
    }
}