package com.sucy.skill.config.category

import com.sucy.skill.facade.api.entity.EntityType
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class ExpYieldsConfig(config: Data) {
    private val killYields = loadYields(config.getOrCreateSection("kill"))
    private val trackBreak = !config.getBoolean("break.allow-replace", true)
    private val breakYields = loadYields(config.getOrCreateSection("break.types"))
    private val placeYields = loadYields(config.getOrCreateSection("place"))
    private val craftYields = loadYields(config.getOrCreateSection("craft"))

    fun killYields(entityType: EntityType): Yields {
        return killYields[entityType.id] ?: NO_YIELD
    }

    private fun loadYields(config: Data): Map<String, Yields> {
        val defaults = config.getSection(DEFAULT)?.let { data ->
            data.keys().map { it to data.getDouble(it) }.toMap()
        } ?: emptyMap()

        val overrides = config.keys()
                .filter { it != DEFAULT }
                .flatMap { name ->
                    val subSection = config.getSection(name)!!
                    subSection.keys().map { YieldEntry(name, it, subSection.getDouble(it)) }
                }
                .groupBy { it.type }
                .mapValues { entry -> entry.value.associate { it.name to it.exp } }

        val allKeys = defaults.keys + overrides.keys

        return allKeys.associate { it to Yields(defaults[it] ?: 0.0, overrides[it] ?: emptyMap()) }
    }

    private data class YieldEntry(val name: String, val type: String, val exp: Double)

    companion object {
        private val DEFAULT = "default"
        private val NO_YIELD = Yields(0.0, emptyMap())
    }
}

data class Yields(val default: Double, val overrides: Map<String, Double>)