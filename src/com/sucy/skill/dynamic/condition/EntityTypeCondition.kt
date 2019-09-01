package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.text.enumName

class EntityTypeCondition : Condition() {
    override val key = "entity type"

    private var types = setOf("PLAYER")

    override fun initialize() {
        super.initialize()
        types = metadata.getStringList("types", types.toList())
                .map { it.enumName() }
                .toSet()
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return types.contains(recipient.type.id)
    }
}