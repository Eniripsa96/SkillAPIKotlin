package com.sucy.skill.util.math.shape

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector3

object PointShape : Shape {
    private val POINTS = listOf(Vector3())
    override fun getPositions(level: Int, caster: Actor, target: Actor): List<Vector3> = POINTS
}