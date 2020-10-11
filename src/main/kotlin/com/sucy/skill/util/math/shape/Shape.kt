package com.sucy.skill.util.math.shape

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.collection.CircularSequence
import com.sucy.skill.util.math.Vector3

interface Shape {
    val isFinite: Boolean

    fun getPositions(level: Int, caster: Actor, target: Actor): List<Vector3> =
        getPositionSequence(level, caster, target).toList()

    fun getPositions(level: Int, caster: Actor, target: Actor, count: Int): List<Vector3> {
        val iterator = CircularSequence(getPositionSequence(level, caster, target))
        return List(count) { iterator.next() }
    }

    fun getPositionSequence(level: Int, caster: Actor, target: Actor): Sequence<Vector3>
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