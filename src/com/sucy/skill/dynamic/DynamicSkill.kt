package com.sucy.skill.dynamic

import com.sucy.skill.api.skill.PassiveSkill
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.api.skill.SkillShot
import com.sucy.skill.dynamic.trigger.Trigger
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.entity.Actor
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class DynamicSkill(name: String, icon: Item, maxLevel: Int)
    : Skill(name, icon, maxLevel), SkillShot, PassiveSkill {

    private val triggers = ArrayList<TriggerHandler>()
    private val iconKeyMapping = HashMap<String, Effect>()
    private val activeLevels = HashMap<UUID, Int>()

    private var castEffect: Trigger<*>? = null
    private var initializeEffect: Trigger<*>? = null
    private var cleanupEffect: Trigger<*>? = null

    val castable: Boolean
        get() { return castEffect != null }

    fun isActive(actor: Actor): Boolean {
        return activeLevels.containsKey(actor.uuid)
    }

    fun getActiveLevel(actor: Actor): Int {
        return activeLevels.getOrDefault(actor.uuid, 0)
    }

    fun registerEvents() {

    }

    override fun update(user: Actor, prevLevel: Int, newLevel: Int) {
        initialize(user, newLevel)
    }

    override fun initialize(user: Actor, level: Int) {
        initializeEffect?.trigger(user, user, level)
        activeLevels[user.uuid] = level
    }

    override fun stopEffects(user: Actor, level: Int) {
        activeLevels.remove(user.uuid)
        triggers.forEach { it.cleanup(user) }

        castEffect?.cleanUp(user)
        initializeEffect?.cleanUp(user)
        cleanupEffect?.trigger(user, user, level)
    }

    override fun apply(caster: Actor, level: Int, target: Actor): Boolean {
        return castEffect?.trigger(caster, target, level) ?: false
    }
}