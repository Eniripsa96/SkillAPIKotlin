package com.sucy.skill.config.category

import com.google.common.collect.ImmutableMap
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class ExpYieldsConfig(config: Data) {
    val yieldsEnabled = config.getBoolean("enabled", false)
    val trackBreak = !config.getBoolean("break.allow-replace", true)
    val breakYields = loadYields(config.getOrCreateSection("break.types"))
    val placeYields = loadYields(config.getOrCreateSection("place"))
    val craftYields = loadYields(config.getOrCreateSection("craft"))

    private fun loadYields(config: Data): Map<String, Map<String, Double>>? {
        val yields = ImmutableMap.builder<String, Map<String, Double>>()
        config.keys().forEach { className ->
            val classYields = ImmutableMap.builder<String, Double>()
            val classConfig = config.getSection(className)!!
            classConfig.keys().forEach { key ->
                classYields.put(key.toUpperCase().replace(' ', '_'), classConfig.getDouble(key))
            }
            yields.put(className, classYields.build())
        }
        return yields.build()
    }
}