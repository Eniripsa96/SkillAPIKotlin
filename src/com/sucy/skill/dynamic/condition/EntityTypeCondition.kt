package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.EntityType
import com.sucy.skill.facade.api.entity.VanillaEntityType

class EntityTypeCondition : Condition() {
    override val key = "entity type"

    private var types: Set<EntityType> = setOf(VanillaEntityType.PLAYER)

    override fun initialize() {
        super.initialize()
        types = metadata.getStringList("types", types.map { it.id }.toList())
                .map { EntityType.of(it) }
                .toSet()
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return types.contains(recipient.type)
    }
}