package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import com.sucy.skill.util.math.shape.PointShape
import com.sucy.skill.util.math.shape.Shape

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