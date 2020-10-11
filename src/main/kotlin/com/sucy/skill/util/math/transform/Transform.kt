package com.sucy.skill.util.math.transform

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula

interface Transform {
    fun apply(pos: Vector3, level: Int, caster: Actor, target: Actor, step: Int): Vector3 =
        apply(listOf(pos), level, caster, target, step).first()

    fun apply(points: List<Vector3>, level: Int, caster: Actor, target: Actor, step: Int): List<Vector3>

    fun evaluate(formula: DynamicFormula, caster: Actor, target: Actor, level: Int, step: Int): Double {
        return formula.evaluate(caster, target, "v" to level.toDouble(), "n" to step.toDouble())
    }
}