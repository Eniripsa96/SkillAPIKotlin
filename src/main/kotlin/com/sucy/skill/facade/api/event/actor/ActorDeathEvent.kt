package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
data class ActorDeathEvent(
        val actor: Actor,
        val killer: Actor?,
        val drops: List<Item>,
        val exp: Int
) : Event