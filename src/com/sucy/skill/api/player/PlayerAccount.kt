package com.sucy.skill.api.player

import com.sucy.skill.api.profession.ProfessionSet
import com.sucy.skill.api.skill.SkillSet
import com.sucy.skill.facade.api.entity.Actor

/**
 * A player account with which professions and skills they own.
 */
data class PlayerAccount internal constructor(val owner: Actor) {
    val professionSet = ProfessionSet()
    val skillSet = SkillSet()
}