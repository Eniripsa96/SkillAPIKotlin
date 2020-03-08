package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class GUISettings(config: Data) {
    val enableFixedHealthBar = config.getBoolean("old-health-bar")
    val enableAggressiveHealthScaling = config.getBoolean("force-scaling")
    val levelBar = config.getString("level-bar")
    val foodBar = config.getString("food-bar")
    val levelText = config.getString("class-level-text")
    val enableActionBar = config.getBoolean("use-action-bar")
    val actionBarText = config.getStringList("action-bar-text")
    val enableScoreboard = config.getBoolean("scoreboard-enabled")
    val enableClassNamePrefix = config.getBoolean("show-class-name")
    val enableClassLevelDisplay = config.getBoolean("show-class-level")
    val enableBoundSkillDisplay = config.getBoolean("show-binds")
    val boundSkillDisplayText = config.getString("show-bind-text")
    val enableTitleDisplays = config.getBoolean("title-enabled")
    val titleDisplayTicks = (20 * config.getDouble("title-duration")).toInt()
    val titleDisplayFadeInTicks = (20 * config.getDouble("title-fade-in")).toInt()
    val titleDisplayFadeOutTicks = (20 * config.getDouble("title-fade-out")).toInt()
    val titleDisplayMessages = config.getStringList("title-messages").toSet()
}