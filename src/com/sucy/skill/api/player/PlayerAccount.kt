package com.sucy.skill.api.player

import com.sucy.skill.api.profession.ProfessionSet
import com.sucy.skill.api.skill.SkillSet
import com.sucy.skill.api.values.ValueSet
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.internal.data.InternalLocation
import com.sucy.skill.util.math.Vector3

/**
 * A player account with which professions and skills they own.
 */
class PlayerAccount {
    val professionSet = ProfessionSet()
    val skillSet = SkillSet()

    var health: Double = 0.0
    var mana: Double = 0.0
    var maxMana: Double = 0.0
    var food: Double = 0.0
    var attributePoints = 0
    var location: Location = InternalLocation("world", Vector3(), 0.0, 0.0)
    var inventory: Array<Item>? = null
    var attributes = ValueSet()
    var values = ValueSet()

    val manaRegen: Double
        get() {
            return professionSet.all.map { it.manaRegen }.sum()
        }
}