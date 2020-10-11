package com.sucy.skill.facade.api.data.effect

import com.sucy.skill.util.math.Vector3

data class ParticleData(
    val particle: Particle,
    val offset: Vector3 = Vector3.ZERO,
    val speed: Float = 1f,
    val amount: Int = 0,
    val data: Any? = null
)