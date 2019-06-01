package com.sucy.skill.api.profession

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.Levelable
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.util.math.formula.Formula

/**
 * SkillAPIKotlin © 2018
 */
open class Profession(name: String, icon: Item, maxLevel: Int) : Levelable(name, icon, maxLevel) {

    val parents: List<Profession>
        get() = parentNames.mapNotNull(SkillAPI.registry::getProfession)

    val skills: List<Skill>
        get() = skillNames.mapNotNull { SkillAPI.registry.getSkill(it) }

    val skillNames = mutableSetOf<String>()
    val parentNames = mutableSetOf<String>()
    val unusableItems = mutableSetOf<String>()

    var group: String = "class"
    var needsPermission: Boolean = false

    var maxMana: Formula = Formula.const(20.0)
    var maxHealth: Formula = Formula.const(20.0)
    var manaRegen: Formula = Formula.const(1.0)

    var prefix: String = name
    var actionBar: String = ""
    var manaName: String = ""

    var skillTreePattern: SkillTreePattern = SkillTreePattern.FLOOD
    val expSources = mutableSetOf<ExpSource>()
}