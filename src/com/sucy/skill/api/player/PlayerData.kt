package com.sucy.skill.api.player

import com.sucy.skill.api.profession.ProfessionSet
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
data class PlayerData internal constructor(val owner: Actor) {
    val professionSet = ProfessionSet()
}