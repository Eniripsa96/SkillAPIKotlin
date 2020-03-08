package com.sucy.skill.dynamic.common

import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.match
import com.sucy.skill.util.text.enumName
import com.sucy.skill.util.text.uncolor

data class ItemContext(private val metadata: Data) {
    private val slotType = ItemSlot::class.match(metadata.getString("slot-type", "ANY"), ItemSlot.ANY)
    private val slots = metadata.getStringList("slots", listOf("0")).map { it.toInt() }

    private val checkMaterial = metadata.getBoolean("check-material")
    private val checkDurability = metadata.getBoolean("check-durability")
    private val checkData = metadata.getBoolean("check-data")
    private val checkLore = metadata.getBoolean("check-lore")
    private val checkName = metadata.getBoolean("check-name")
    private val regex = metadata.getBoolean("regex")

    private val material = metadata.getString("material", "arrow").enumName()
    private val durability = metadata.getInt("durability").toShort()
    private val data = metadata.getInt("data").toByte()
    private val lore = metadata.getString("lore", "")
    private val name = metadata.getString("name", "")

    private val itemAggregation = AggregationType::class.match(
            metadata.getString("item-aggregation", "first"), AggregationType.FIRST
    )
    private val inventoryAggregation = AggregationType::class.match(
            metadata.getString("inventory-aggregation", "sum"), AggregationType.SUM
    )

    private val nameRegex = if (regex) Regex(lore) else MATCH_ALL
    private val loreRegex = if (regex) Regex(name) else MATCH_ALL
    private val valueLoreRegex = if (lore.contains(VALUE_FILTER)) {
        Regex(lore.replace(VALUE_FILTER, DOUBLE_REGEX))
    } else {
        Regex(DOUBLE_REGEX)
    }

    fun findItems(actor: Actor, handler: (Item) -> Boolean): Boolean {
        if (slotType == ItemSlot.ANY) return findSlots(actor) {
            _, item, _ -> handler(item)
        }

        val passed = slots.firstOrNull { slot ->
            slotType.getItem(actor, slot)?.let { handler.invoke(it) } ?: false
        }
        return passed != null
    }

    fun findSlots(actor: Actor, handler: (ActorInventory, Item, Int) -> Boolean): Boolean {
        val inventory = actor.inventory
        for (i in 0 until inventory.size) {
            val item = inventory[i]
            if (item != null
                    && (!checkMaterial || item.type.id == material)
                    && (!checkDurability || item.durability == durability)
                    && (!checkData || item.data == data)
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
            item.lore.any { loreRegex.matches(it.uncolor()) }
        } else {
            item.lore.any { it.uncolor().contains(lore) }
        }
    }

    fun checkName(item: Item): Boolean {
        return if (regex) {
            nameRegex.matches(name.uncolor())
        } else {
            name.uncolor().contentEquals(name)
        }
    }

    fun getValue(actor: Actor): Double {
        var result = 0.0
        findItems(actor) { item ->
            val found = item.lore.mapNotNull {
                valueLoreRegex.matchEntire(it.uncolor())?.groupValues?.first()?.toDouble()
            }
            val aggregate = combine(found, itemAggregation)
            result = combine(listOf(result, aggregate), inventoryAggregation)

            false
        }
        return result
    }

    fun combine(values: List<Double>, aggregationType: AggregationType): Double = when (aggregationType) {
        AggregationType.FIRST -> values.first()
        AggregationType.SUM -> values.sum()
        AggregationType.PRODUCT -> values.reduce { a, b -> a * b }
        AggregationType.MAX -> values.max() ?: 0.0
    }

    enum class AggregationType {
        FIRST,
        SUM,
        PRODUCT,
        MAX
    }

    companion object {
        val MATCH_ALL: Regex = Regex(".*")
        const val VALUE_FILTER = "{value}"
        const val DOUBLE_REGEX = "([+-]?[0-9]+([.,][0-9]+)?)"
    }
}