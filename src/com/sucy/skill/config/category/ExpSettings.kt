package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data
import com.sucy.skill.util.math.formula.DynamicFormula

/**
 * SkillAPIKotlin © 2018
 */
class ExpSettings(config: Data) {
    val useDroppedExp = config.getBoolean("use-exp-orbs", true)
    val blockMobSpawner = config.getBoolean("block-mob-spawner", true)
    val blockMobEgg = config.getBoolean("block-mob-egg", true)
    val blockCreative = config.getBoolean("block-creative", true)
    val loseExpBlacklist = config.getStringList("lose-exp-blacklist", emptyList())
    val formula = if (config.getBoolean("use-custom")) {
        config.getFormula("custom-formula", 100.0)
    } else {
        parseFormula(config.getOrCreateSection("formula"))
    }

    private fun parseFormula(config: Data): DynamicFormula {
        val x = config.getDouble("x", 1.0)
        val y = config.getDouble("y", 8.0)
        val z = config.getDouble("z", 16.0)

        return DynamicFormula("$x*x*x+$y*x+$z")
    }
}