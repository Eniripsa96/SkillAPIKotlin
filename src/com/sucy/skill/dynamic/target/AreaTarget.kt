package com.sucy.skill.dynamic.target

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class AreaTarget : TargetEffect() {
    override val key = "area"

    private lateinit var radius: DynamicFormula

    override fun initialize() {
        super.initialize()
        radius = metadata.getFormula("radius", 3.0)
    }

    override fun getTargets(context: CastContext, target: Actor): List<Actor> {
        val radius = compute(this.radius, context, target)
        return SkillAPI.server.getWorld(target.location.world).getActorsInRadius(target.location.coords, radius)
    }
}
