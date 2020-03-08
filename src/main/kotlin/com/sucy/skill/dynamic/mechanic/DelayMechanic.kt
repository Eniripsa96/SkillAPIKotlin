package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.managers.Task
import com.sucy.skill.util.math.toTicks

class DelayMechanic : Effect() {
    override val key = "delay"
    override val type = EffectType.MECHANIC

    private var delay = 40L

    override fun initialize() {
        delay = metadata.getDouble("delay", 2.0).toTicks()
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        var task: Task? = null
        task = SkillAPI.server.taskManager.run(delay) {
            executeChildren(context.copy(cancellable = task), targets)
        }
        return true
    }

}