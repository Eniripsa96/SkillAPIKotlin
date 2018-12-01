package com.sucy.skill.dynamic

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.Icon
import com.sucy.skill.api.RPGElement
import com.sucy.skill.dynamic.trigger.TriggerEffect
import com.sucy.skill.facade.api.entity.Actor
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class DynamicSkill(name: String, icon: Icon, maxLevel: Int)
    : RPGElement(name, icon, maxLevel) {

    private val triggers = ArrayList<TriggerHandler>()
    private val iconKeyMapping = HashMap<String, Effect>()
    private val activeLevels = HashMap<UUID, Int>()

    private var castEffect: TriggerEffect? = null
    private var initializeEffect: TriggerEffect? = null
    private var cleanupEffect: TriggerEffect? = null

    val castable: Boolean
        get() { return castEffect != null }

    fun isActive(actor: Actor): Boolean {
        return activeLevels.containsKey(actor.uuid)
    }

    fun getActiveLevel(actor: Actor): Int {
        return activeLevels.getOrDefault(actor.uuid, 0)
    }

    fun registerEvents() {
        triggers.forEach {
            SkillAPI.eventBus
        }
    }
}