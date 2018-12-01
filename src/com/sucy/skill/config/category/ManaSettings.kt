package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class ManaSettings(config: Data) {
    val enableMana = config.getBoolean("enabled")
    val manaRechargeFrequencyInTicks = (config.getDouble("freq") * 20).toInt()
}