package com.sucy.skill.util.math.transform

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.math.shape.Plane
import com.sucy.skill.util.math.toRadians
import kotlin.math.cos
import kotlin.math.sin

data class RotateTransform(private val plane: Plane, private val angleInDegrees: DynamicFormula) : Transform {
    override fun apply(points: List<Vector3>, level: Int, caster: Actor, target: Actor, step: Int): List<Vector3> {
        val angle = evaluate(angleInDegrees, caster, target, level, step).toRadians()
        val cos = cos(angle)
        val sin = sin(angle)

        return when (plane) {
            Plane.XY -> points.map { Vector3(it.x * cos - it.y * sin, it.x * sin + it.y * cos, it.z) }
            Plane.XZ -> points.map { Vector3(it.x * cos - it.z * sin, it.y, it.x * sin + it.z * cos) }
            Plane.YZ -> points.map { Vector3(it.x, it.y * cos - it.z * sin, it.y * sin + it.z * cos) }
        }
    }
}