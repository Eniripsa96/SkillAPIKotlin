package com.sucy.skill.config.category

import com.sucy.skill.facade.api.entity.EntityType
import com.sucy.skill.facade.api.entity.VanillaEntityType
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.text.enumName

class ActorSizes(config: Data) {
    private val settings: Map<EntityType, Size> = config.keys().map {
        val section = config.getOrCreateSection(it)
        val size = Size(section.getDouble("size", 1.0), section.getDouble("height", 2.0))
        VanillaEntityType.valueOf(it.enumName()) to size
    }.toMap()

    fun sizeOf(type: EntityType): Size {
        return settings[type] ?: DEFAULT_SIZE
    }

    data class Size(val diameter: Double, val height: Double)

    companion object {
        private val DEFAULT_SIZE = Size(1.0, 2.0)
    }
}