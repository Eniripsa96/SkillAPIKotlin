package com.sucy.skill.facade.api.event.world

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.data.Block
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
interface BlockPlaceEvent : Event, Cancellable {
    val block: Block
    val actor: Actor?
}