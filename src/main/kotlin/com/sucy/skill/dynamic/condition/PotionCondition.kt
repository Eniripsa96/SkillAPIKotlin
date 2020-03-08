package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.text.enumName

class PotionCondition : AbstractNumberCondition() {
    override val key = "potion"

    private var types: Set<String> = setOf("POISON")

    override fun initialize() {
        super.initialize()
        types = metadata.getStringList("type", listOf("POISON"))
                .map { it.enumName() }
                .toSet()
    }

    override fun getValue(actor: Actor, context: CastContext, target: Actor): Double {
        return actor.potionEffects.filter { types.contains(it.type.id) }
                .map { it.tier }
                .max()?.toDouble()
                ?: -1.0
    }
}