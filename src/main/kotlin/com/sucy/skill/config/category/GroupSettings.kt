package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data
import com.sucy.skill.util.log.Logger
import java.lang.Exception

class GroupSettings(config: Data) {
    val defaultClass = config.getString("default", "none")
    val permission = config.getString("permission", "none")
    val scoreboard = config.getBoolean("show-scoreboard", true)
    val canReset = config.getBoolean("can-reset", true)
    val friendly = config.getBoolean("friendly", false)
    val expLostOnDeath = config.getDouble("exp-lost-on-death")

    // Reset on profess
    val resetLevel = config.getBoolean("profess-reset.level", false)
    val resetAttributes = config.getBoolean("profess-reset.attributes", false)
    val resetSkills = config.getBoolean("profess-reset.skills", false)

    // Points
    val startingSkillPoints = config.getInt("starting-skill-points", 1)
    val skillPointsPerLevel = config.getInt("skill-points-per-level", 1)
    val startingAttributePoints = config.getInt("starting-attribute-points", 0)
    val attributePointsPerLevel = config.getInt("attribute-points-per-level", 1)

    // Custom points
    val useCustomSkillPoints = config.getBoolean("use-custom-skill-points", false)
    val useCustomAttributePoints = config.getBoolean("use-custom-attribute-points", false)
    val customSkillPoints = loadPoints(config, "custom-skill-points")
    val customAttributePoints = loadPoints(config, "custom-attribute-points")

    companion object {
        private fun loadPoints(config: Data, key: String) {
            val data = config.getOrCreateSection(key)
            data.keys().mapNotNull {
                try {
                    it.toInt() to data.getInt(it)
                } catch (ex: Exception) {
                    Logger.error("Group settings have invalid points for $key: $it and ${data.getString(it)} should both be numbers")
                    null
                }
            }.toMap()
        }
    }
}