package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
data class ActorSpawnEvent(
        val actor: Actor,
        override var cancelled: Boolean = false
) : Event, Cancellable