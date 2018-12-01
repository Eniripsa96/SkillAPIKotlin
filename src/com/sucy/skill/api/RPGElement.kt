package com.sucy.skill.api

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
open class RPGElement(
        protected var name: String,
        protected var icon: Icon,
        protected var maxLevel: Int) {

    protected val key = name.toLowerCase()

    protected var description = ArrayList<String>()
    protected val metadata = Data()
    protected val conditions = ArrayList<LevelCondition>()


}
