package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.text.enumName

class BiomeCondition : Condition() {
    override val key = "biome"

    private var biome = "plains"

    override fun initialize() {
        super.initialize()

        biome = metadata.getString("biome", "plains").enumName()
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val loc = recipient.location.coords
        val biome = recipient.world.getBiome(
                loc.x.toInt(),
                loc.y.toInt(),
                loc.z.toInt()
        )

        return biome.enumName() == this.biome
    }
}