package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.TimerType
import com.sucy.skill.api.values.Value
import com.sucy.skill.api.values.ValueType
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

abstract class AbstractValueMechanic : Mechanic() {
    private var stack = true
    private var maxStacks = 100
    private var duration = 5.0
    private var valueType = ValueType.BONUS
    private var timerType = TimerType.OVERWRITE
    private var stackKey = "default"

    abstract fun getValue(target: Actor): Value

    override fun initialize() {
        super.initialize()

        stack = metadata.getBoolean("stack", stack)
        maxStacks = metadata.getInt("maxStacks", maxStacks)
        duration = metadata.getDouble("duration", duration)
        valueType = ValueType.valueOf(metadata.getString("valueType", valueType.name))
        timerType = TimerType.valueOf(metadata.getString("timerType", timerType.name))
        stackKey = metadata.getString("stackKey", stackKey)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val value = getValue(recipient)
        val modifier = compute("value", context.caster, target)
        if (stack) {
            when (valueType) {
                ValueType.BASE -> value.addBaseStack(modifier, duration, maxStacks, timerType, stackKey)
                ValueType.BONUS -> value.addBonusStack(modifier, duration, maxStacks, timerType, stackKey)
                ValueType.MULTIPLIER -> value.addMultiplierStack(modifier, duration, maxStacks, timerType, stackKey)
            }
        } else {
            when (valueType) {
                ValueType.BASE -> value.addBase(modifier, stackKey)
                ValueType.BONUS -> value.addBonus(modifier, stackKey)
                ValueType.MULTIPLIER -> value.addMultiplier(modifier, stackKey)
            }
        }
        return true
    }
}