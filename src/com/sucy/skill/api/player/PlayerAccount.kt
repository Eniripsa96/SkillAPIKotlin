package com.sucy.skill.api.player

import com.sucy.skill.api.profession.ProfessionSet
import com.sucy.skill.api.skill.SkillSet
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import java.util.*

/**
 * A player account with which professions and skills they own.
 */
class PlayerAccount {
    val professionSet = ProfessionSet()
    val skillSet = SkillSet()

    var health: Double? = null
    var mana: Double = 0.0
    var maxMana: Double = 0.0
    var food: Double? = null
    var location: Location? = null
    var inventory: Array<Item>? = null
}