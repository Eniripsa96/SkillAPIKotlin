package com.sucy.skill.facade.api.data.inventory

import kotlin.math.max
import kotlin.math.min

interface Inventory {
    val size: Int

    val contents: List<Item?>

    operator fun get(index: Int): Item?
    operator fun set(index: Int, item: Item?)

    fun give(item: Item): Item?

    fun isEmpty(slot: Int): Boolean {
        val item = this[slot]
        return item == null || item.type.id.equals("AIR", ignoreCase = true)
    }

    fun getOpenSlots(min: Int = 0, max: Int = size - 1): Int {
        var count = 0
        val lower = max(0, min)
        val upper = min(size - 1, max)
        for (i in lower..upper) {
            if (isEmpty(i)) count++
        }
        return count
    }
}