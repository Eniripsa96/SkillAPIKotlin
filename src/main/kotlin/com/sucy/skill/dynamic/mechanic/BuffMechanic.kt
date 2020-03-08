package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.BuffType
import com.sucy.skill.api.values.Value
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import com.sucy.skill.util.text.enumName

class BuffMechanic : AbstractValueMechanic() {
    override val key = "buff"

    private var valueKey = "api.buff.dmg"

    override fun initialize() {
        super.initialize()

        val buffType = BuffType::class.match(metadata.getString("buffType", "damage"), BuffType.DAMAGE)
        val category = metadata.getString("category")

        valueKey = buffType.getKey(category)
    }

    override fun getValue(target: Actor): Value {
        return target.values[valueKey]
    }
}
