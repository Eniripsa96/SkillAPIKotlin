package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.Value
import com.sucy.skill.facade.api.entity.Actor

class ValueMechanic : AbstractValueMechanic() {
    override val key = "value"

    private var valueKey = "default"

    override fun initialize() {
        super.initialize()
        valueKey = metadata.getString("key", valueKey)
    }

    override fun getValue(target: Actor): Value {
        return target.values[valueKey]
    }
}