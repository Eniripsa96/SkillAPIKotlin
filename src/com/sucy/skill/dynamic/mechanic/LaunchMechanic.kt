package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.formula.DynamicFormula

class LaunchMechanic : Mechanic() {
    override val key = "launch"

    private lateinit var forward: DynamicFormula
    private lateinit var upward: DynamicFormula
    private lateinit var right: DynamicFormula

    private var relative = Relativity.TARGET

    override fun initialize() {
        super.initialize()

        forward = metadata.getFormula("forward")
        upward = metadata.getFormula("upward")
        right = metadata.getFormula("right")

        relative = Relativity::class.match(metadata.getString("relative", "target"), relative)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val forward = compute(this.forward, context.caster, target)
        val upward = compute(this.upward, context.caster, target)
        val right = compute(this.right, context.caster, target)

        val dir = when (relative) {
            Relativity.CASTER -> context.caster.location.forward.flatNormal
            Relativity.TARGET -> target.location.forward.flatNormal
            Relativity.BETWEEN -> (target.location.coords - context.caster.location.coords).normal
        }

        val result = dir * forward + dir.cross(UP) * right
        result.y = upward

        recipient.velocity = result

        return true
    }

    enum class Relativity {
        TARGET,
        CASTER,
        BETWEEN
    }

    companion object {
        val UP = Vector3(y = 1.0)
    }
}