package com.sucy.skill.api.attribute

import com.sucy.skill.dynamic.Effect
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.limit

sealed class AttributeSet {
    abstract val attributes: Collection<Attribute>

    protected fun apply(actor: Actor, value: Double, handler: (Attribute, Int, Double) -> Double): Double {
        var result = value
        attributes.forEach {
            val amount = limit(
                    num = actor.attributes[it.key].total,
                    lower = 0.0,
                    upper = it.max.toDouble()
            ).toInt()
            result = handler(it, amount, result)
        }
        return result
    }
}

data class StatAttributeSet(val stat: String, override val attributes: Collection<Attribute>) : AttributeSet() {
    fun apply(actor: Actor, value: Double): Double {
        return apply(actor, value) { attribute, amount, result -> attribute.modify(stat, result, amount) }
    }

    companion object {
        val EMPTY = StatAttributeSet("empty", emptyList())
    }
}

data class DynamicAttributeSet(val key: String, override val attributes: Collection<Attribute>) : AttributeSet() {
    fun apply(actor: Actor, effect: Effect, value: Double): Double {
        return apply(actor, value) { attribute, amount, result -> attribute.modify(effect, key, result, amount) }
    }

    companion object {
        val EMPTY = DynamicAttributeSet("empty", emptyList())
    }
}
