package com.sucy.skill.api.profession

import com.sucy.skill.api.Icon
import com.sucy.skill.api.LevelCondition
import com.sucy.skill.api.Levelable
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
open class Profession(name: String, icon: Icon, maxLevel: Int) : Levelable(name, icon, maxLevel) { }