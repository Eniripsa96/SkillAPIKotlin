package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.toTicks

class DelayMechanic : Effect() {
    override val key = "delay"
    override val type = EffectType.MECHANIC

    private var delay = 40L

    override fun initialize() {
        delay = metadata.getDouble("delay", delay * 20.0).toTicks()
    }

    override fun execute(caster: Actor, level: Int, targets: List<Actor>): Boolean {
        SkillAPI.server.taskManager.run(Runnable {
            executeChildren(caster, level, targets)
        }, delay)
        return true
    }

}