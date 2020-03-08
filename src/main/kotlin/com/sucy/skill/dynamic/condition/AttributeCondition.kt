package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class AttributeCondition : AbstractNumberCondition() {
    override val key = "attribute"

    private var attribute: String = "strength"

    override fun initialize() {
        super.initialize()

        attribute = metadata.getString("attribute", attribute)
    }

    override fun getValue(actor: Actor, context: CastContext, target: Actor): Double = actor.attributes[attribute].total
}