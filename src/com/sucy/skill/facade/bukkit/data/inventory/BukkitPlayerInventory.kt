package com.sucy.skill.facade.bukkit.data.inventory

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.bukkit.BukkitUtil
import com.sucy.skill.facade.bukkit.data.BukkitItem
import org.bukkit.inventory.PlayerInventory

data class BukkitPlayerInventory(private val inventory: PlayerInventory) : ActorInventory {
    override var heldItemSlot: Int
        get() = inventory.heldItemSlot
        set(value) { inventory.heldItemSlot = value }
    override val contents: List<Item?>
        get() = inventory.contents.map { item -> item?.let { BukkitItem(item) } }
    override var mainHand: Item?
        get() = inventory.itemInMainHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInMainHand = value?.let(BukkitUtil::toBukkit) }
    override var offHand: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.let(BukkitUtil::toBukkit) }
    override var helmet: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.let(BukkitUtil::toBukkit) }
    override var chestplate: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.let(BukkitUtil::toBukkit) }
    override var leggings: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.let(BukkitUtil::toBukkit) }
    override var boots: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.let(BukkitUtil::toBukkit) }
    override val size: Int
        get() = inventory.size

    override fun get(index: Int): Item? {
        return inventory.getItem(index)?.let { BukkitItem(it) }
    }

    override fun set(index: Int, item: Item?) {
        val bukkitItem = item?.let(BukkitUtil::toBukkit)
        inventory.setItem(index, bukkitItem)
    }
}