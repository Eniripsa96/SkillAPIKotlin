package com.sucy.skill.listener

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.api.profession.ExpSource
import com.sucy.skill.config.category.Yields
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent

class ExpListener {

    @Listen(Step.REACT, ignoreCancelled = true)
    fun onDeath(event: ActorDeathEvent) {
        if (event.killer !is Player) return

        val exp = when {
            SkillAPI.settings.exp.useDroppedExp -> Yields(event.exp.toDouble(), emptyMap())
            else -> SkillAPI.settings.expYields.killYields(event.actor.type)
        }
        event.killer.activeAccount.professionSet.giveExp(event.killer, exp.default, ExpSource.KILL, exp.overrides)
    }
}