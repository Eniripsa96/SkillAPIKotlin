package com.sucy.skill.api.profession

import com.sucy.skill.api.Levelable
import com.sucy.skill.facade.api.data.Item

/**
 * SkillAPIKotlin © 2018
 */
open class Profession(name: String, icon: Item, maxLevel: Int) : Levelable(name, icon, maxLevel) {
    val skills = HashSet<String>()
}