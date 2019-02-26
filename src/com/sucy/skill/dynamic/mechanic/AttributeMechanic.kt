package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.TimerType
import com.sucy.skill.api.values.ValueType
import com.sucy.skill.facade.api.entity.Actor

class AttributeMechanic : Mechanic() {
    override val key = "values"

    private var attribute = "vitality"
    private var value = 1.0
    private var stack = true
    private var maxStacks = 100
    private var duration = 5.0
    private var valueType = ValueType.BONUS
    private var timerType = TimerType.OVERWRITE
    private var stackKey = "default"

    override fun initialize() {
        super.initialize()

        attribute = metadata.getString("attribute", attribute)
        value = metadata.getDouble("value", value)
        stack = metadata.getBoolean("stack", stack)
        maxStacks = metadata.getInt("maxStacks", maxStacks)
        duration = metadata.getDouble("duration", duration)
        valueType = ValueType.valueOf(metadata.getString("valueType", valueType.name))
        timerType = TimerType.valueOf(metadata.getString("timerType", timerType.name))
        stackKey = metadata.getString("stackKey", stackKey)
    }

    override fun execute(caster: Actor, level: Int, target: Actor): Boolean {
        val value = target.attributes[attribute]
        if (stack) {
            when (valueType) {
                ValueType.BASE -> value.addBaseStack(this.value, duration, maxStacks, timerType, stackKey)
                ValueType.BONUS -> value.addBonusStack(this.value, duration, maxStacks, timerType, stackKey)
                ValueType.MULTIPLIER -> value.addMultiplierStack(this.value, duration, maxStacks, timerType, stackKey)
            }
        } else {
            when (valueType) {
                ValueType.BASE -> value.addBase(this.value, stackKey)
                ValueType.BONUS -> value.addBonus(this.value, stackKey)
                ValueType.MULTIPLIER -> value.addMultiplier(this.value, stackKey)
            }
        }
        return true
    }
}