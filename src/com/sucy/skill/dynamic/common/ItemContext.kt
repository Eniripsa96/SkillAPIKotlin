package com.sucy.skill.dynamic.common

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.text.enumName
import com.sucy.skill.util.text.uncolor
import java.util.regex.Pattern

data class ItemContext(private val metadata: Data) {
    private val checkMaterial = metadata.getBoolean("check-material")
    private val checkDurability = metadata.getBoolean("check-durability")
    private val checkLore = metadata.getBoolean("check-lore")
    private val checkName = metadata.getBoolean("check-name")
    private val regex = metadata.getBoolean("regex")

    private val material = metadata.getString("material", "").enumName()
    private val durability = metadata.getInt("durability").toShort()
    private val lore = metadata.getString("lore", "")
    private val name = metadata.getString("name", "")

    private val namePattern = if (regex) Pattern.compile(lore) else MATCH_ALL
    private val lorePattern = if (regex) Pattern.compile(name) else MATCH_ALL
    private val valueLorePattern = if (lore.contains(VALUE_FILTER)) {
        Pattern.compile(lore.replace(VALUE_FILTER, DOUBLE_PATTERN))
    } else {
        Pattern.compile(DOUBLE_PATTERN)
    }

    fun findSlots(actor: Actor, caster: Actor, target: Actor, handler: (ActorInventory, Item, Int) -> Boolean): Boolean {
        val inventory = actor.inventory
        for (i in 0 until inventory.size) {
            val item = inventory[i]
            if (item != null
                    && (!checkMaterial || item.type.enumName() == material)
                    && (!checkDurability || item.durability == durability)
                    && (!checkLore || checkLore(item))
                    && (!checkName || checkName(item))) {
                if (handler(inventory, item, i)) {
                    return true
                }
            }
        }

        return false
    }

    fun checkLore(item: Item): Boolean {
        return if (regex) {
            item.lore.any { lorePattern.matcher(it.uncolor()).find() }
        } else {
            item.lore.any { it.uncolor().contains(lore) }
        }
    }

    fun checkName(item: Item): Boolean {
        return if (regex) {
            namePattern.matcher(name.uncolor()).find()
        } else {
            name.uncolor().contentEquals(name)
        }
    }

    companion object {
        val MATCH_ALL: Pattern = Pattern.compile(".*")
        const val VALUE_FILTER = "{value}"
        const val DOUBLE_PATTERN = "([+-]?[0-9]+([.,][0-9]+)?)"
    }
}