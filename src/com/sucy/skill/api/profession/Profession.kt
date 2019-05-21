package com.sucy.skill.api.profession

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.Levelable
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.util.math.formula.DynamicFormula

/**
 * SkillAPIKotlin © 2018
 */
open class Profession(name: String, icon: Item, maxLevel: Int) : Levelable(name, icon, maxLevel) {

    val parents: List<Profession>
        get() = parentNames.mapNotNull(SkillAPI.registry::getProfession)

    val skills: List<Skill>
        get() = skillNames.mapNotNull { SkillAPI.registry.getSkill(it) }

    val skillNames = HashSet<String>()
    val parentNames = HashSet<String>()
    val unusableItems = HashSet<String>()

    var group: String = "class"
    var needsPermission: Boolean = false

    var maxMana: DynamicFormula = DynamicFormula("20")
    var maxHealth: DynamicFormula = DynamicFormula("20")
    var manaRegen: DynamicFormula = DynamicFormula("1.0")

    var prefix: String = name
    var actionBar: String = ""
    var manaName: String = ""
}