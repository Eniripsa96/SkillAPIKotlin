package com.sucy.skill.facade.bukkit.data.inventory

import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.bukkit.toBukkit
import org.bukkit.inventory.PlayerInventory

data class BukkitPlayerInventory(private val inventory: PlayerInventory) : ActorInventory {
    override var heldItemSlot: Int
        get() = inventory.heldItemSlot
        set(value) { inventory.heldItemSlot = value }
    override val contents: List<Item?>
        get() = inventory.contents.map { item -> item?.let { BukkitItem(item) } }
    override var mainHand: Item?
        get() = inventory.itemInMainHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInMainHand = value?.toBukkit() }
    override var offHand: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.toBukkit() }
    override var helmet: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.toBukkit() }
    override var chestplate: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.toBukkit() }
    override var leggings: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.toBukkit() }
    override var boots: Item?
        get() = inventory.itemInOffHand?.let { BukkitItem(it) }
        set(value) { inventory.itemInOffHand = value?.toBukkit() }
    override val size: Int
        get() = inventory.size

    override fun get(index: Int): Item? {
        return inventory.getItem(index)?.let { BukkitItem(it) }
    }

    override fun set(index: Int, item: Item?) {
        val bukkitItem = item?.toBukkit()
        inventory.setItem(index, bukkitItem)
    }

    override fun give(item: Item): Item? {
        val failed = inventory.addItem(item.toBukkit())
        return if (failed.isEmpty()) null else failed[0]?.let { BukkitItem(it) }
    }
}