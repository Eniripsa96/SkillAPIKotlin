package com.sucy.skill.api

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.math.formula.Formula

/**
 * SkillAPIKotlin © 2018
 */
open class Levelable(
        var name: String,
        var icon: Item,
        var maxLevel: Int) {

    val key = name.toLowerCase()

    protected var description = ArrayList<String>()
    protected val metadata = Data()
    protected val conditions = ArrayList<LevelCondition>()
    var expCurve = Formula.const(1.0)
}

