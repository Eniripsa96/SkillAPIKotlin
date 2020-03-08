package com.sucy.skill.config.category

import com.sucy.skill.data.loader.impl.common.ItemDataLoader
import com.sucy.skill.util.io.Data

class SkillBarSettings(config: Data) {
    val enabled = config.getBoolean("enabled", false)
    val showCooldown = config.getBoolean("show-cooldown", true)
    val unassignedIcon = ItemDataLoader.loadOrDefault(config.getSection("unassigned-icon"))

    private val skillSlots = parseSlots(config.getOrCreateSection("layout"), "skill")
    private val lockedSlots = parseSlots(config.getOrCreateSection("layout"), "locked")

    fun isSkillSlot(slot: Int): Boolean {
        return skillSlots.contains(slot)
    }

    fun isLockedSlot(slot: Int): Boolean {
        return lockedSlots.contains(slot)
    }

    private fun parseSlots(config: Data, key: String): Set<Int> {
        return config.keys().filter {
            val isSkillSlot = config.getSection(it)?.getBoolean(key, false) ?: false
            isSkillSlot && it.matches(INT_PATTERN)
        }.map { it.toInt() }.toSet()
    }

    companion object {
        val INT_PATTERN = Regex("[0-9]+")
    }
}