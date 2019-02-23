package com.sucy.skill.dynamic

import com.sucy.skill.dynamic.trigger.Trigger
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
class TriggerHandler(val skill: DynamicSkill, val key: String, val trigger: Trigger<*>, val component: Trigger<*>) {
    fun cleanup(actor: Actor) {
        component.cleanUp(actor)
    }
}