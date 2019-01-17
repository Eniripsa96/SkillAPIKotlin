package com.sucy.skill.api.profession

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.Levelable

class ProfessionSet {
    private val professions = HashMap<String, Levelable>()

    val all: Collection<Levelable>
        get() { return professions.values }

    val main: Levelable?
        get() { return this[SkillAPI.settings.account.mainGroup]; }

    operator fun ProfessionSet.get(group: String): Levelable? {
        return professions[group]
    }

    operator fun ProfessionSet.set(group: String, profession: Levelable) {

    }

    fun isProfessed(): Boolean {
        return !professions.isEmpty()
    }

    fun isProfessed(group: String): Boolean {
        return professions.containsKey(group)
    }
}