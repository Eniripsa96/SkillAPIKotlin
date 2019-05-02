package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

class WorldSettings(config: Data) {
    private val worlds = config.getStringList("worlds").toSet()
    private val useAsEnabling = config.getBoolean("use-as-enabling")
    private val restrictWorlds = config.getBoolean("enable")

    fun isWorldEnabled(world: String): Boolean {
        return !restrictWorlds || (useAsEnabling == worlds.contains(world))
    }
}