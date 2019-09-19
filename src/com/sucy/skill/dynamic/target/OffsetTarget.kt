package com.sucy.skill.dynamic.target

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.fakes.FakeActor
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.internal.data.InternalLocation
import com.sucy.skill.util.math.Vector3

class OffsetTarget : TargetEffect() {
    override val key = "offset"

    private var horizontal = false
    private var forward = 0.0
    private var upward = 0.0
    private var right = 0.0

    override fun initialize() {
        super.initialize()

        horizontal = metadata.getBoolean("horizontal", horizontal)
        forward = metadata.getDouble("forward", forward)
        upward = metadata.getDouble("upward", upward)
        right = metadata.getDouble("right", right)
    }

    override fun getTargets(from: Actor, context: CastContext, target: Actor): List<Actor> {
        val loc = from.location
        val dir = if (horizontal) loc.forward.flatNormal else loc.forward
        val normal = dir.cross(UP)
        val up = dir.cross(normal)
        val pos = dir * forward + normal * right + up * upward

        return listOf(FakeActor(InternalLocation(loc.world, pos, loc.yaw, loc.pitch)))
    }

    companion object {
        val UP = Vector3(0.0, 1.0, 0.0)
    }
}