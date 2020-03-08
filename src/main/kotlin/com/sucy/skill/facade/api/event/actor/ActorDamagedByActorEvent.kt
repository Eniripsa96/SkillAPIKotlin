package com.sucy.skill.facade.api.event.actor

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
data class ActorDamagedByActorEvent(
        val actor: Actor,
        val source: DamageSource,
        val damageType: String,
        val attacker: Actor,
        var amount: Double,
        override var cancelled: Boolean = false
) : Event, Cancellable

enum class DamageSource {
    ATTACK,
    PROJECTILE,
    SKILL,
    ENVIRONMENT,
    OTHER
}