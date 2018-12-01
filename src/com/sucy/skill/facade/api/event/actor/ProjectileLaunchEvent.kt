package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Projectile

/**
 * SkillAPIKotlin © 2018
 */
interface ProjectileLaunchEvent : Event, Cancellable {
    val projectile: Projectile
}