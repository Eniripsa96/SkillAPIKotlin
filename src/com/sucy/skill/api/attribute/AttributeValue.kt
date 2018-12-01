package com.sucy.skill.api.attribute

/**
 * SkillAPIKotlin © 2018
 */
class AttributeValue {
    private var base = 0
    private var bonus = 0
    private var multiplier = 1.0

    private val baseSources = HashMap<String, Int>()
    private val additiveSource = HashMap<String, Int>()
    private val multiplierSources = HashMap<String, Double>()

    var total = 0
        private set

    /**
     * Adds extra points to the attribute value. This is applied before
     * any other bonuses and only allows one [bonus] per source, with the
     * latest application overwriting older bonuses.
     */
    fun addBase(amount: Int, source: String) {
        base += amount - (baseSources.put(source, amount) ?: 0)
        updateTotal()
    }

    /**
     * Adds extra points to the attribute value. This is applied after
     * any other bonuses and only allows one bonus per [source], with the
     * latest application overwriting older bonuses.
     */
    fun addBonus(amount: Int, source: String) {
        bonus += amount - (additiveSource.put(source, amount) ?: 0)
        updateTotal()
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

    /**
     * Clears all bonuses from [source] for the attribute
     */
    fun clear(source: String) {
        base -= baseSources.remove(source) ?: 0
        bonus -= additiveSource.remove(source) ?: 0
        multiplier /= multiplierSources.remove(source) ?: 1.0
        updateTotal()
    }

    /**
     * Clears all bonuses from sources starting with [prefix]
     */
    fun clearByPrefix(prefix: String) {
        clearKeySet(prefix, baseSources.keys)
        clearKeySet(prefix, additiveSource.keys)
        clearKeySet(prefix, multiplierSources.keys)
        updateTotal()
    }

    private fun clearKeySet(prefix: String, keys: MutableSet<String>) {
        keys.filter { it.startsWith(prefix) }.forEach { clear(it) }
    }

    private fun updateTotal() {
        total = Math.max(0, (base * multiplier + bonus).toInt())
    }
}
