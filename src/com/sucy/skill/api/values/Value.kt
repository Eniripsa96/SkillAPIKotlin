package com.sucy.skill.api.values

import java.util.function.Consumer

/**
 * SkillAPIKotlin © 2018
 */
class Value {
    private var base = 0.0
    private var bonus = 0.0
    private var multiplier = 1.0

    internal val baseSources = HashMap<String, Double>()
    internal val additiveSources = HashMap<String, Double>()
    internal val multiplierSources = HashMap<String, Double>()

    private val baseStacks = HashMap<String, ValueStacks>()
    private val bonusStacks = HashMap<String, ValueStacks>()
    private val multiplierStacks = HashMap<String, ValueStacks>()

    var total = 0.0
        private set

    /**
     * Adjusts the external [value] based on the added bonuses.
     * The [value] is added onto the [base] value and otherwise follows
     * the normal [total] calculation.
     */
    fun adjust(value: Double): Double {
        return Math.max(0.0, ((base + value) * multiplier + bonus))
    }

    /**
     * Adds extra points to the attribute value. This is applied before
     * any other bonuses and only allows one [bonus] per source, with the
     * latest application overwriting older bonuses.
     */
    fun addBase(amount: Double, source: String) {
        base += amount - (baseSources.put(source, amount) ?: 0.0)
        updateTotal()
    }

    fun addBaseStack(amount: Double, duration: Double, maxStacks: Int, type: TimerType, source: String) {
        val stacks = baseStacks.computeIfAbsent(source) {
            ValueStacks(0.0, Consumer { addBase(it, source) })
        }
        stacks.apply(amount, duration, type, maxStacks)
    }

    /**
     * Adds extra points to the attribute value. This is applied after
     * any other bonuses and only allows one bonus per [source], with the
     * latest application overwriting older bonuses.
     */
    fun addBonus(amount: Double, source: String) {
        bonus += amount - (additiveSources.put(source, amount) ?: 0.0)
        updateTotal()
    }

    fun addBonusStack(amount: Double, duration: Double, maxStacks: Int, type: TimerType, source: String) {
        val stacks = bonusStacks.computeIfAbsent(source) {
            ValueStacks(0.0, Consumer { addBonus(it, source) })
        }
        stacks.apply(amount, duration, type, maxStacks)
    }

    /**
     * Adds a multiplier to the attribute value. This is applied before bonuses
     * applied via [addBonus] but after bonuses applied via [addBase]. Different
     * multipliers are additive and giving a multiplier for a [source] will replace
     * any other bonuses under that same [source].
     */
    fun addMultiplier(factor: Double, source: String) {
        require(factor > 0) { "Factor must be a positive number" }
        multiplier *= factor / (multiplierSources.put(source, factor) ?: 1.0)
        updateTotal()
    }

    fun addMultiplierStack(amount: Double, duration: Double, maxStacks: Int, type: TimerType, source: String) {
        val stacks = multiplierStacks.computeIfAbsent(source) {
            ValueStacks(1.0, Consumer { addMultiplier(it, source) })
        }
        stacks.apply(amount, duration, type, maxStacks)
    }

    /**
     * Clears all bonuses from [source] for the attribute
     */
    fun clear(source: String) {
        base -= baseSources.remove(source) ?: 0.0
        bonus -= additiveSources.remove(source) ?: 0.0
        multiplier /= multiplierSources.remove(source) ?: 1.0
        updateTotal()
    }

    /**
     * Clears all bonuses from sources starting with [prefix]
     */
    fun clearByPrefix(prefix: String) {
        clearKeySet(prefix, baseSources.keys)
        clearKeySet(prefix, additiveSources.keys)
        clearKeySet(prefix, multiplierSources.keys)
        updateTotal()
    }

    private fun clearKeySet(prefix: String, keys: MutableSet<String>) {
        keys.filter { it.startsWith(prefix) }.forEach { clear(it) }
    }

    private fun updateTotal() {
        total = base * multiplier + bonus
    }
}
