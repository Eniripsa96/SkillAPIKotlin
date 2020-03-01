package com.sucy.skill.util.math.shape

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector3

interface Shape {
    fun getPositions(level: Int, caster: Actor, target: Actor): List<Vector3>
}

enum class Plane { XY, YZ, XZ }

enum class Axis {
    X_POSITIVE,
    X_NEGATIVE,
    Y_POSITIVE,
    Y_NEGATIVE,
    Z_POSITIVE,
    Z_NEGATIVE
}