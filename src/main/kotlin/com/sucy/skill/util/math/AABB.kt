package com.sucy.skill.util.math

import com.sucy.skill.facade.api.entity.Actor

object AABB {
    fun closestPoint(actor: Actor, point: Vector3): Vector3 {
        val (x, y, z) = actor.location.coords
        val (diameter, height) = actor.size
        val rad = diameter / 2

        return Vector3(
            limit(point.x, x - rad, x + rad),
            limit(point.y, y, y + height),
            limit(point.z, z - rad, z + rad)
        )
    }

    fun farthestPoint(actor: Actor, point: Vector3): Vector3 {
        val diff = actor.location.coords - point
        val (diameter, height) = actor.size
        val rad = diameter / 2

        return Vector3(
            point.x + maxAbs(diff.x - rad, diff.x + rad),
            point.y + maxAbs(diff.y, diff.y + height),
            point.z + maxAbs(diff.z - rad, diff.z + rad)
        )
    }
}