package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.internal.data.InternalLocation
import com.sucy.skill.util.match
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula

class WarpMechanic : Mechanic() {
    override val key = "warp"

    private var positionType = PositionType.RELATIVE

    private lateinit var x: DynamicFormula
    private lateinit var y: DynamicFormula
    private lateinit var z: DynamicFormula

    private var world: String? = null
    private var yaw: Double? = null
    private var pitch: Double? = null

    private var throughWalls = false

    override fun initialize() {
        super.initialize()

        positionType = PositionType::class.match(metadata.getString("type"), positionType)
        x = metadata.getFormula("x", 0.0)
        y = metadata.getFormula("y", 0.0)
        z = metadata.getFormula("z", 0.0)
        world = metadata.getString("world")
        yaw = metadata.getDoubleOrNull("yaw")
        pitch = metadata.getDoubleOrNull("pitch")
        throughWalls = metadata.getBoolean("through-walls", throughWalls)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val loc = recipient.location
        val coords = loc.coords

        val x = compute(x, context, target)
        val y = compute(y, context, target)
        val z = compute(z, context, target)
        val world = world ?: loc.world
        val yaw = yaw ?: loc.yaw
        val pitch = pitch ?: loc.pitch

        when (positionType) {
            PositionType.ABSOLUTE -> target.moveTo(x, y, z, world, yaw, pitch)
            PositionType.RELATIVE -> {
                val newLoc = InternalLocation(world, Vector3(x + coords.x, y + coords.y, z + coords.z), yaw, pitch)

            }
        }

        return true
    }

    private enum class PositionType {
        RELATIVE,
        ABSOLUTE
    }
}