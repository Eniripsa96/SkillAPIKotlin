package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class CeilingCondition : Condition() {
    override val key = "ceiling"

    private lateinit var distance: DynamicFormula
    private var atLeast = true

    override fun initialize() {
        super.initialize()

        distance = metadata.getFormula("distance", 5.0)
        atLeast = metadata.getBoolean("at-least", atLeast)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val distance = compute(this.distance, context, target).toInt()

        val world = recipient.world
        val loc = recipient.location.coords
        val x = loc.x.toInt()
        val y = loc.y.toInt()
        val z = loc.z.toInt()

        for (i in 2 until distance) {
            if (world.getBlock(x, y + i, z).isSolid) {
                return !atLeast
            }
        }

        return atLeast
    }

}