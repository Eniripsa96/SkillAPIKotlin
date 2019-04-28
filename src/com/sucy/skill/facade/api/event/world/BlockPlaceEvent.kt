package com.sucy.skill.facade.api.event.world

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.data.Block
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
data class BlockPlaceEvent(
    val block: Block,
    val actor: Actor?,
    override var cancelled: Boolean = false
) : Event, Cancellable