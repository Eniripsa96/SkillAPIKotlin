package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class  TargetingConfig(config: Data) {
    val monsterWorlds = config.getStringList("monsters-enemy").toSet()
    val hostileMonsters = monsterWorlds.isEmpty() && config.getBoolean("monsters-enemy")
    val animalWorlds = config.getStringList("passive-ally").toSet()
    val friendlyAnimals = animalWorlds.isEmpty() && config.getBoolean("passive-ally")
    val playerWorlds = config.getStringList("actor-ally").toSet()
    val friendlyPlayers = animalWorlds.isEmpty() && config.getBoolean("actor-ally")

    val enableParties = config.getBoolean("parties-ally")
    val attackNpcs = config.getBoolean("affect-npcs")
    val attackStands = config.getBoolean("affect-armor-stands")
}