package com.sucy.skill.api

import com.sucy.skill.facade.api.entity.Actor
import kotlin.math.max

/**
 * Handles recording experience and levels for [Levelable] objects
 * for a specific [Actor], such as a player's profession.
 */
open class LevelProgress<T : Levelable>(val data: T) {
    var exp = 0.0
        set(value) { max(0.0, value) }
    var totalExp = 0.0
        set(value) { max(0.0, value) }
    var level = 1
        set(value) { max(1, value) }
    val requiredExp: Double
        get() { return computeRequiredExp(level) }
    val isMaxLevel: Boolean
        get() { return data.maxLevel <= level }
    val projectedTotalExp: Double
        get() {
            var exp = 0.0
            for (i in 1 until data.maxLevel) {
                exp += computeRequiredExp(i)
            }
            return exp
        }

    fun giveExp(amount: Double, source: String) {
        if (amount <= 0 || isMaxLevel) return

        exp += amount
        totalExp += amount
        checkLevelUp()
    }

    fun giveLevels(amount: Int) {
        val gainedLevels = Math.min(amount, data.maxLevel - level)
        if (gainedLevels <= 0) return

        var gainedExp = requiredExp - exp
        for (i in 1 until amount) {
            gainedExp += computeRequiredExp(level + i)
        }

        level += gainedLevels
        totalExp += gainedExp
        exp = 0.0
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