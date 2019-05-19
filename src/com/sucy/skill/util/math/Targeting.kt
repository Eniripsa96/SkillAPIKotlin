package com.sucy.skill.util.math

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import kotlin.math.abs
import kotlin.math.cos

object Targeting {

    fun isInFront(source: Location, target: Vector3): Boolean {
        val facing = source.forward
        val relative = target - source.coords
        return facing.dot(relative) >= 0.0
    }

    fun isInFront(source: Location, target: Vector3, radians: Double): Boolean {
        return when {
            radians <= 0.0 -> false
            radians >= 360 -> true
            radians == 180.0 -> isInFront(source, target)
            else -> {
                val facing = source.forward
                val relative = target - source.coords
                return facing.dot(relative) >= cos(radians)
            }
        }
    }

    fun getClosestLineOfSightTarget(location: Location, range: Double): Actor? {
        val found = getLineOfSightTargets(location, range)
        return if (found.isEmpty()) null else found.first()
    }

    fun getLineOfSightTargets(location: Location, range: Double): List<Actor> {
        val direction = location.forward

        val start = location.coords
        val end = start + direction * range

        val candidates = SkillAPI.server.getWorld(location.world).getActorsInRadius(start, range)
        return candidates.filter {
            val pos = it.location.coords
            val size = it.size

            val min = pos.copy()
            min.x -= size.diameter * 0.5
            min.z -= size.diameter * 0.5

            val max = pos.copy()
            max.x += size.diameter * 0.5
            max.y += size.height
            max.z += size.diameter * 0.5

            intersects(start, end, min, max)
        }
    }

    private fun intersects(start: Vector3, end: Vector3, min: Vector3, max: Vector3): Boolean {
        val d = (end - start) * 0.5
        val e = (max - min) * 0.5
        val c = start + d - (min + max) * 0.5
        val ad = d.abs()

        return when {
            abs(c.x) > e.x + ad.x -> false
            abs(c.y) > e.y + ad.y -> false
            abs(c.z) > e.z + ad.z -> false
            abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y -> false
            abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z -> false
            abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x -> false
            else -> true
        }
    }
}