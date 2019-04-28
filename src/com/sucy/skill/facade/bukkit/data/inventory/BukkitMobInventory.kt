package com.sucy.skill.facade.bukkit.data.inventory

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.bukkit.BukkitUtil
import com.sucy.skill.facade.bukkit.data.BukkitItem
import org.bukkit.inventory.EntityEquipment

data class BukkitMobInventory(private val equipment: EntityEquipment): ActorInventory {
    override var mainHand: Item?
        get() = equipment.itemInMainHand?.let { BukkitItem(it) }
        set(value) { equipment.itemInMainHand = value?.let(BukkitUtil::toBukkit) }
    override var offHand: Item?
        get() = equipment.itemInOffHand?.let { BukkitItem(it) }
        set(value) { equipment.itemInOffHand = value?.let(BukkitUtil::toBukkit) }
    override var helmet: Item?
        get() = equipment.helmet?.let { BukkitItem(it) }
        set(value) { equipment.helmet = value?.let(BukkitUtil::toBukkit) }
    override var chestplate: Item?
        get() = equipment.chestplate?.let { BukkitItem(it) }
        set(value) { equipment.chestplate = value?.let(BukkitUtil::toBukkit) }
    override var leggings: Item?
        get() = equipment.leggings?.let { BukkitItem(it) }
        set(value) { equipment.leggings = value?.let(BukkitUtil::toBukkit) }
    override var boots: Item?
        get() = equipment.boots?.let { BukkitItem(it) }
        set(value) { equipment.boots = value?.let(BukkitUtil::toBukkit) }
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
}