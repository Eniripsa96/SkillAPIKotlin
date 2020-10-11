package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.text.enumName

class BiomeCondition : Condition() {
    override val key = "biome"

    private var biomes = listOf("plains")

    override fun initialize() {
        super.initialize()

        biomes = metadata.getStringList("biome", biomes).map { it.enumName() }
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val loc = recipient.location.coords
        val biome = recipient.world.getBiome(
                loc.x.toInt(),
                loc.y.toInt(),
                loc.z.toInt()
        )

        return biomes.contains(biome.enumName())
    }
}