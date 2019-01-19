package com.sucy.skill.api

import com.sucy.skill.facade.api.entity.Actor

/**
 * Handles recording experience and levels for [Levelable] objects
 * for a specific [Actor], such as a player's profession.
 */
open class LevelProgress<T : Levelable>(val owner: Actor, val data: T) {
    var exp = 0.0
        private set
    var totalExp = 0.0
        private set
    var level = 0
        private set
    val requiredExp: Double
        get() { return computeRequiredExp(level) }
    val isMaxLevel: Boolean
        get() { return data.maxLevel <= level }

    fun giveExp(amount: Double, source: String) {
        if (amount <= 0 || isMaxLevel) return

        // TODO - trigger event

        exp += amount
        totalExp += amount
        checkLevelUp()
    }

    fun giveLevels(amount: Int) {
        val gained = Math.min(amount, data.maxLevel - level)
        if (gained <= 0) return

        level += gained

        // TODO - trigger event
    }

    private fun checkLevelUp() {
        var levels = -1
        var required = 0.0
        do {
            exp -= required
            levels++
            required = computeRequiredExp(level + levels)
        } while (exp >= required)

        giveLevels(levels)
    }

    private fun computeRequiredExp(level: Int): Double {
        return Math.max(0.0, Math.floor(data.expCurve.evaluate(level.toDouble())))
    }
}