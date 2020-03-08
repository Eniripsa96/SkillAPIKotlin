package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.TimerType
import com.sucy.skill.api.values.Value
import com.sucy.skill.api.values.ValueType
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import com.sucy.skill.util.math.formula.DynamicFormula
import java.util.*

abstract class AbstractValueMechanic : Mechanic() {
    private var valueType = ValueType.BONUS
    private var timerType = TimerType.OVERWRITE
    private var stackKey = "default"

    private lateinit var duration: DynamicFormula
    private lateinit var maxStacks: DynamicFormula
    private lateinit var value: DynamicFormula

    abstract fun getValue(target: Actor): Value

    override fun initialize() {
        super.initialize()

        maxStacks = metadata.getFormula("maxStacks", 5.0)
        duration = metadata.getFormula("duration", 5.0)
        value = metadata.getFormula("value", 1.0)
        valueType = ValueType::class.match(metadata.getString("valueType", valueType.name), ValueType.SET)
        timerType = TimerType::class.match(metadata.getString("timerType", timerType.name), TimerType.OVERWRITE)
        stackKey = metadata.getString("stackKey", stackKey)

        if (stackKey.isEmpty()) stackKey = UUID.randomUUID().toString()
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val value = getValue(recipient)
        val modifier = compute(this.value, context, target)
        val duration = compute(this.duration, context, target)
        val maxStacks = compute(this.maxStacks, context, target).toInt()
        if (duration > 0) {
            when (valueType) {
                ValueType.SET -> value.setTo(modifier, stackKey)
                ValueType.BASE -> value.addBaseStack(modifier, duration, maxStacks, timerType, stackKey)
                ValueType.BONUS -> value.addBonusStack(modifier, duration, maxStacks, timerType, stackKey)
                ValueType.MULTIPLIER -> value.addMultiplierStack(modifier, duration, maxStacks, timerType, stackKey)
            }
        } else {
            when (valueType) {
                ValueType.SET -> value.setTo(modifier, stackKey)
                ValueType.BASE -> value.addBase(modifier, stackKey)
                ValueType.BONUS -> value.addBonus(modifier, stackKey)
                ValueType.MULTIPLIER -> value.addMultiplier(modifier, stackKey)
            }
        }
        return true
    }
}