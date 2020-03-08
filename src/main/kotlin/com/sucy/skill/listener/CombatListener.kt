package com.sucy.skill.listener

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent

class CombatListener : SkillAPIListener {

    override fun cleanup() {
        SkillAPI.entityData.combatTimers.clear()
    }

    @Listen(Step.REACT, ignoreCancelled = true)
    fun onDamaged(event: ActorDamagedByActorEvent) {
        event.attacker.markInCombat()
        event.actor.markInCombat()
    }
}