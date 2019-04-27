package com.sucy.skill.facade.bukkit.data.inventory

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.bukkit.BukkitUtil
import com.sucy.skill.facade.bukkit.data.BukkitItem
import org.bukkit.inventory.PlayerInventory

data class BukkitPlayerInventory(private val inventory: PlayerInventory) : ActorInventory {
    override var mainHand: Item
        get() = BukkitItem(inventory.itemInMainHand)
        set(value) { inventory.itemInMainHand = BukkitUtil.toBukkit(value) }
    override var offHand: Item
        get() = BukkitItem(inventory.itemInOffHand)
        set(value) { inventory.itemInOffHand = BukkitUtil.toBukkit(value) }
    override var helmet: Item
        get() = BukkitItem(inventory.itemInOffHand)
        set(value) { inventory.itemInOffHand = BukkitUtil.toBukkit(value) }
    override var chestplate: Item
        get() = BukkitItem(inventory.itemInOffHand)
        set(value) { inventory.itemInOffHand = BukkitUtil.toBukkit(value) }
    override var leggings: Item
        get() = BukkitItem(inventory.itemInOffHand)
        set(value) { inventory.itemInOffHand = BukkitUtil.toBukkit(value) }
    override var boots: Item
        get() = BukkitItem(inventory.itemInOffHand)
        set(value) { inventory.itemInOffHand = BukkitUtil.toBukkit(value) }
    override val size: Int
        get() = inventory.size

    override fun get(index: Int): Item? {
        return inventory.getItem(index)?.let { BukkitItem(it) }
    }

}