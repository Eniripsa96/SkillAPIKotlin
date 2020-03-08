package com.sucy.skill.api

import com.sucy.skill.api.profession.ExpSource
import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.util.text.DynamicFilter
import com.sucy.skill.util.text.context.ActorFilterContext
import com.sucy.skill.util.text.context.FilterContext
import com.sucy.skill.util.text.enumName
import kotlin.math.max

/**
 * Handles recording experience and levels for [Levelable] objects
 * for a specific [Actor], such as a player's profession.
 */
abstract class LevelProgress<T : Levelable>(val data: T) {
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

    abstract val filterContext: FilterContext<*>

    fun giveExp(amount: Double, source: ExpSource, overrides: Map<String, Double> = emptyMap()): Boolean {
        return if (data.expSources.contains(source)) forceGiveExp(amount, overrides) else false
    }

    fun forceGiveExp(amount: Double, overrides: Map<String, Double> = emptyMap()): Boolean {
        val actual = if (overrides.isEmpty()) {
            amount
        } else {
            overrides[data.key.toLowerCase()] ?: overrides[data.name.toLowerCase()] ?: amount
        }

        if (actual <= 0 || isMaxLevel) return false
        exp += actual
        totalExp += actual
        return checkLevelUp()
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

    private fun checkLevelUp(): Boolean {
        var levels = -1
        var required = 0.0
        do {
            exp -= required
            levels++
            required = computeRequiredExp(level + levels)
        } while (exp >= required)

        giveLevels(levels)
        return levels > 0
    }

    private fun computeRequiredExp(level: Int): Double {
        return Math.max(0.0, Math.floor(data.expCurve.evaluate(level.toDouble())))
    }

    fun iconFor(player: Player): Item {
        val actorFilter = ActorFilterContext("player", player)
        val context = this.filterContext
        val icon = data.icon

        return icon.copyWith(
                name = icon.name?.let { DynamicFilter.apply(it, context, actorFilter) },
                lore = icon.lore.map { DynamicFilter.apply(it, context, actorFilter) }
        )
    }
}