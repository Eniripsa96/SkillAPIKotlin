package com.sucy.skill.config.category

import com.sucy.skill.casting.combos.Click
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class ComboSettings(config: Data) {
    val enableCombos = config.getBoolean("enabled")
    val enableCustomCombos = config.getBoolean("allow-custom")
    val enableAutoAssignedCombos = config.getBoolean("auto-assign")
    val comboMaxSize = config.getInt("combo-size")
    val comboClickTtlTicks = (1000 * config.getDouble("click-time")).toInt()
    val enabledClicks = Click.values().map { config.getBoolean("use-click-${it.configKey()}") }
}
