package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.data.effect.ParticleData
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.internal.data.InternalLocation
import com.sucy.skill.util.collection.CircularSequence
import com.sucy.skill.util.match
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.shape.PointShape
import com.sucy.skill.util.math.shape.Shape
import com.sucy.skill.util.math.toTicks
import com.sucy.skill.util.math.transform.Transform

class ParticleMechanic : Mechanic() {
    override val key = "particle"

    private var effectType = EffectType.STATIC
    private var shape: Shape = PointShape
    private var path: Shape = PointShape
    private var copies: Int = 1

    override fun initialize() {
        super.initialize()

        effectType = EffectType::class.match(metadata.getString("effect-type")) ?: effectType
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {

        return true
    }

    enum class EffectType {
        STATIC,
        ANIMATED
    }
}

data class ParticleEffect(
    val shape: Shape,
    val shapeCount: Int,
    val path: Shape,
    val transforms: List<Transform>,
    val particleData: ParticleData,
    val copies: Int
) {
    fun playFrame(
        caster: Actor,
        target: Actor,
        recipient: Actor,
        level: Int,
        frame: Int = 0,
        path: CircularSequence<Vector3>? = null
    ) {
        val points = if (shape.isFinite) {
            shape.getPositions(level, caster, target)
        } else shape.getPositions(level, caster, target, shapeCount)

        val world = recipient.world
        val loc = recipient.location
        val rotation = loc.forward.flatNormal
        val offset = loc.coords
        val position = path?.next() ?: caster.location.coords

        points.forEach { point ->
            var result = point
            transforms.forEach { result = it.apply(result, level, caster, target, frame) }
            val final = position + result.apply {
                Vector3(
                    x = x * rotation.x - z * rotation.y + offset.x,
                    y = y + offset.y,
                    z = x * rotation.y + z * rotation.x + offset.z
                )
            }
            world.playParticle(particleData, InternalLocation(loc.world, final, 0.0, 0.0))
        }
    }

    fun play(
        caster: Actor,
        target: Actor,
        recipient: Actor,
        level: Int,
        durationInSeconds: Double,
        periodInSeconds: Double
    ) {
        var i = 0
        SkillAPI.scheduler.run(0L, periodInSeconds.toTicks()) {
            playFrame(caster, target, recipient, level, i++)
        }
    }
}