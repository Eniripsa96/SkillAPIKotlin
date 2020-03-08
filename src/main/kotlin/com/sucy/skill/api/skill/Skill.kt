package com.sucy.skill.api.skill

import com.sucy.skill.api.Levelable
import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.entity.Actor

abstract class Skill(name: String, icon: Item, maxLevel: Int) : Levelable(name, icon, maxLevel) {
    abstract fun apply(caster: Actor, level: Int, target: Actor): Boolean

    open fun getTarget(caster: Actor, level: Int): Actor? {
        return caster
    }

    fun cast(caster: Actor, level: Int): Boolean {
        val target = getTarget(caster, level) ?: return false
        return apply(caster, level, target)
    }
}