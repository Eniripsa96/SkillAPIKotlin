package com.sucy.skill.api.profession

import com.sucy.skill.api.Icon
import com.sucy.skill.api.LevelCondition
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
open class Profession(
        private var name: String,
        private var icon: Icon,
        private var maxLevel: Int) {

    val key = name.toLowerCase()

    protected val description = ArrayList<String>()
    protected val metadata = Data()
    protected val conditions = ArrayList<LevelCondition>()
}