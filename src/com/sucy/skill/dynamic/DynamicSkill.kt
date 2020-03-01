package com.sucy.skill.dynamic

import com.sucy.skill.api.skill.PassiveSkill
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.api.skill.SkillShot
import com.sucy.skill.dynamic.trigger.Trigger
import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.Formula
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class DynamicSkill(
        name: String,
        icon: Item,
        maxLevel: Int,
        private val triggers: List<Trigger<*>>
) : Skill(name, icon, maxLevel), SkillShot, PassiveSkill {


    private val iconKeyMapping = mutableMapOf<String, Effect>()
    private val activeLevels = mutableMapOf<UUID, Int>()

    private var castEffect: Trigger<*>? = null
    private var initializeEffect: Trigger<*>? = null
    private var cleanupEffect: Trigger<*>? = null

    override var cooldown = Formula.const(0.0)

    val castable: Boolean
        get() = castEffect != null

    fun isActive(uuid: UUID): Boolean = activeLevels.containsKey(uuid)

    fun registerEvents() {
        triggers.forEach { it.initialize() }
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
        triggers.forEach { it.cleanUp(user) }

        castEffect?.cleanUp(user)
        initializeEffect?.cleanUp(user)
        cleanupEffect?.trigger(user, user, level)
    }

    override fun apply(caster: Actor, level: Int, target: Actor): Boolean {
        return castEffect?.trigger(caster, target, level) ?: false
    }
}