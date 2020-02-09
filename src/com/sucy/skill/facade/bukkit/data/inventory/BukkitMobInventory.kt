package com.sucy.skill.facade.bukkit.data.inventory

import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.bukkit.*
import org.bukkit.inventory.EntityEquipment

data class BukkitMobInventory(private val equipment: EntityEquipment): ActorInventory {
    override var mainHand: Item?
        get() = equipment.itemInMainHand?.let { BukkitItem(it) }
        set(value) { equipment.itemInMainHand = value?.toBukkit() }
    override var offHand: Item?
        get() = equipment.itemInOffHand?.let { BukkitItem(it) }
        set(value) { equipment.itemInOffHand = value?.toBukkit() }
    override var helmet: Item?
        get() = equipment.helmet?.let { BukkitItem(it) }
        set(value) { equipment.helmet = value?.toBukkit() }
    override var chestplate: Item?
        get() = equipment.chestplate?.let { BukkitItem(it) }
        set(value) { equipment.chestplate = value?.toBukkit() }
    override var leggings: Item?
        get() = equipment.leggings?.let { BukkitItem(it) }
        set(value) { equipment.leggings = value?.toBukkit() }
    override var boots: Item?
        get() = equipment.boots?.let { BukkitItem(it) }
        set(value) { equipment.boots = value?.toBukkit()
        }
    override var heldItemSlot: Int
        get() = 0
        set(_) {}
    override val size: Int
        get() = 6
    override val contents: List<Item?>
        get() = listOf(mainHand, offHand, helmet, chestplate, leggings, boots)

    override fun get(index: Int): Item? = contents[index]

    override fun set(index: Int, item: Item?) {
        when (index) {
            0 -> mainHand = item
            1 -> offHand = item
            2 -> helmet = item
            3 -> chestplate = item
            4 -> leggings = item
            5 -> boots = item
            else -> throw IndexOutOfBoundsException("Index must be between 0 and 5, found $index")
        }
    }

    override fun give(item: Item): Item? {
        val bukkit = item.toBukkit()
        val type = bukkit.type
        when {
            equipment.helmet == null && type.isHelmet() -> equipment.helmet = bukkit
            equipment.chestplate == null && type.isChestplate() -> equipment.chestplate = bukkit
            equipment.leggings == null && type.isLeggings() -> equipment.leggings = bukkit
            equipment.boots == null && type.isBoots() -> equipment.boots = bukkit
            equipment.itemInMainHand == null -> equipment.itemInMainHand = bukkit
            equipment.itemInOffHand == null -> equipment.itemInOffHand = bukkit
            else -> return item
        }
        return null
    }
}