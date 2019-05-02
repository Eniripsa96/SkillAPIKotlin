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

    val parents by lazy { parentNames.map(SkillAPI.registry::getProfession) }

    val skills = HashSet<Skill>()
    val parentNames = HashSet<String>()
    val blacklist = HashSet<String>()

    var group: String = "class"
    var needsPermission: Boolean = false

    var maxMana: Formula = Formula.const(20.0)
    var maxHealth: Formula = Formula.const(20.0)
    var manaRegen: Formula = Formula.const(1.0)

    var prefix: String = name
    var actionBar: String? = null
    var manaName: String? = null
}