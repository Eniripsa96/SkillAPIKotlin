package com.sucy.skill.dynamic

import com.sucy.skill.api.Icon
import com.sucy.skill.api.Levelable
import com.sucy.skill.api.skill.PassiveSkill
import com.sucy.skill.api.skill.SkillShot
import com.sucy.skill.dynamic.trigger.TriggerEffect
import com.sucy.skill.facade.api.entity.Actor
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class DynamicSkill(name: String, icon: Icon, maxLevel: Int)
    : Levelable(name, icon, maxLevel), SkillShot, PassiveSkill {

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

    override fun cast(user: Actor, level: Int): Boolean {
        return castEffect?.trigger(user, user, level) ?: false
    }
}