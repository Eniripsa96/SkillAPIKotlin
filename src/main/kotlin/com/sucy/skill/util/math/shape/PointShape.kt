package com.sucy.skill.util.math.shape

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.Vector3

object PointShape : Shape {
    private val POINT = Vector3()
    override val isFinite = true
    override fun getPositionSequence(level: Int, caster: Actor, target: Actor) = sequenceOf(POINT)
}