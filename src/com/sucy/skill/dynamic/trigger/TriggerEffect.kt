package com.sucy.skill.dynamic.trigger

import com.google.common.collect.ImmutableList
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType

/**
 * SkillAPIKotlin © 2018
 */
class TriggerEffect : Effect() {
    override val key = "trigger"
    override val type = EffectType.TRIGGER
    var running = false
        private set

    fun trigger(caster: Actor, target: Actor, level: Int): Boolean {
        return execute(caster, level, ImmutableList.of(target))
    }

    override fun execute(caster: Actor, level: Int, targets: List<Actor>): Boolean {
        try {
            running = true
            return executeChildren(caster, level, targets)
        } finally {
            running = false
        }
    }
}