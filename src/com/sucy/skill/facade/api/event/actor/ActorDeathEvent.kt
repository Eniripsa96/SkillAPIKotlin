package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
interface ActorDeathEvent : Event {
    val actor: Actor
    val killer: Actor?
    val drops: List<Item>
    val exp: Int
}