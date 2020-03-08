package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.Value
import com.sucy.skill.facade.api.entity.Actor

class AttributeMechanic : AbstractValueMechanic() {
    override val key = "attribute"

    private var attribute = "vitality"

    override fun initialize() {
        super.initialize()
        attribute = metadata.getString("attribute", attribute)
    }

    override fun getValue(target: Actor): Value {
        return target.attributes[attribute]
    }
}