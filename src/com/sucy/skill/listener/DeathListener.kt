package com.sucy.skill.listener

import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent

class DeathListener {

    @Listen(step = Step.FIRST)
    fun onDeath(event: ActorDeathEvent) {
    }
}