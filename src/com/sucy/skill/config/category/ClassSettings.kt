package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class ClassSettings(config: Data) {
    val enableCustomHealth = config.getBoolean("modify-health")
    val defaultHealth = config.getInt("classless-hp")
    val displayAutoUnlockedSkills = config.getBoolean("show-auto-skills")
    val enableAttributes = config.getBoolean("attributes-enabled")
    val enableDowngradingAttributes = config.getBoolean("attributes-downgrade")
    val levelUpEffectSkillName = config.getString("level-up-skill")
}