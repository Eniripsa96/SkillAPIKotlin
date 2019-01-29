package com.sucy.skill.api.skill

import com.sucy.skill.api.Icon
import com.sucy.skill.api.Levelable
import com.sucy.skill.facade.api.entity.Actor

abstract class Skill(name: String, icon: Icon, maxLevel: Int) : Levelable(name, icon, maxLevel) {
    abstract fun getTarget(caster: Actor, level: Int): Actor?
    abstract fun apply(caster: Actor, level: Int, target: Actor): Boolean

    fun cast(caster: Actor, level: Int): Boolean {
        val target = getTarget(caster, level) ?: return false
        return apply(caster, level, target)
    }
}