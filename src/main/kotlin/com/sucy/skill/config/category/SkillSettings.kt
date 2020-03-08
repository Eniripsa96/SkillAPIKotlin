package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class SkillSettings(config: Data) {
    val enableSkillPointRefunds = config.getBoolean("allow-downgrade")
    val enableKnockbackWhenBlocked = config.getBoolean("knockback-no-damage")
    val enableCastMessages = config.getBoolean("show-messages")
    val castMessageRadius = config.getInt("message-radius")
    // TODO - apply wildcard filters when materials can be resolved
    val protectedBlockTypes = config.getStringList("world-filter")
}