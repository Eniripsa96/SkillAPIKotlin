package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

class ActorSizes(config: Data) {
    private val settings = config.keys().map {
        val section = config.getOrCreateSection(it)
        it.toLowerCase() to Size(section.getDouble("size", 1.0), section.getDouble("height", 2.0))
    }.toMap()

    fun sizeOf(name: String): Size {
        return settings[name.toLowerCase()] ?: DEFAULT_SIZE
    }

    data class Size(val diameter: Double, val height: Double)

    companion object {
        private val DEFAULT_SIZE = Size(1.0, 2.0)
    }
}