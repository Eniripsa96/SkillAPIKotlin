package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class CastSettings(config: Data) {
    val enablePremiumCasting = config.getBoolean("enabled")
    val enableCastBars = config.getBoolean("bars")
    val enableCombatBar = config.getBoolean("combat")
    val premiumCastIconSlot = config.getInt("slot", 9) - 1
    val globalSkillCooldownTicks = config.getInt("cooldown")
    val premiumCastIcon = config.getItem("item")
    val hoverBarIcon = config.getItem("hover-item")
    val quickCastIcon = config.getInt("instant-item")
}