package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Projectile

/**
 * SkillAPIKotlin © 2018
 */
data class ProjectileLaunchEvent(
        val projectile: Projectile,
        override var cancelled: Boolean = false
) : Event, Cancellable