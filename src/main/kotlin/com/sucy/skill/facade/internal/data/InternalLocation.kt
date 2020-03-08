package com.sucy.skill.facade.internal.data

import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.toRadians
import kotlin.math.cos
import kotlin.math.sin

data class InternalLocation(
        override val world: String,
        override val coords: Vector3,
        override val yaw: Double,
        override val pitch: Double
) : Location {
    override val forward: Vector3
        get() {
            val radX = yaw.toRadians()
            val radY = pitch.toRadians()
            val xz = cos(radY)
            return Vector3(
                    -sin(radY),
                    -xz * sin(radX),
                    xz * cos(radX)
            )
        }
}