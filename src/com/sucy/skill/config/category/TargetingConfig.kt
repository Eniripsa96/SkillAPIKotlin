package com.sucy.skill.config.category

import com.google.common.collect.ImmutableSet
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class  TargetingConfig(config: Data) {
    val monsterWorlds = ImmutableSet.copyOf(config.getStringList("monsters-enemy"))
    val hostileMonsters = monsterWorlds.isEmpty() && config.getBoolean("monsters-enemy")
    val animalWorlds = ImmutableSet.copyOf(config.getStringList("passive-ally"))
    val friendlyAnimals = animalWorlds.isEmpty() && config.getBoolean("passive-ally")
    val playerWorlds = ImmutableSet.copyOf(config.getStringList("actor-ally"))
    val friendlyPlayers = animalWorlds.isEmpty() && config.getBoolean("actor-ally")

    val enableParties = config.getBoolean("parties-ally")
    val attackNpcs = config.getBoolean("affect-npcs")
    val attackStands = config.getBoolean("affect-armor-stands")
}