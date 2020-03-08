package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
data class ActorMoveEvent(
        val actor: Actor,
        val from: Location,
        val to: Location,
        override var cancelled: Boolean = false
) : Event, Cancellable