package com.sucy.skill.api

import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.math.formula.Formula

/**
 * SkillAPIKotlin © 2018
 */
abstract class Levelable(
        var name: String,
        var icon: Item,
        var maxLevel: Int) {

    val key = name.toLowerCase()

    val description: MutableList<String> = mutableListOf()
    var metadata = Data()
    val conditions: MutableList<LevelCondition> = mutableListOf()
    var expCurve = Formula.const(1.0)
}

