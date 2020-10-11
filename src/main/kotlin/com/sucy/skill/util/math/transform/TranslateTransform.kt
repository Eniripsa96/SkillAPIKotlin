package com.sucy.skill.util.math.transform

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula

class TranslateTransform(
    val x: DynamicFormula,
    val y: DynamicFormula,
    val z: DynamicFormula
) : Transform {
    override fun apply(points: List<Vector3>, level: Int, caster: Actor, target: Actor, step: Int): List<Vector3> {
        val offset = Vector3(
            x = evaluate(x, caster, target, level, step),
            y = evaluate(y, caster, target, level, step),
            z = evaluate(z, caster, target, level, step)
        )

        return points.map { it + offset }
    }
}