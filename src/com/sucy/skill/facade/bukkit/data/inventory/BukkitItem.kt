package com.sucy.skill.facade.bukkit.data.inventory

import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.data.inventory.ItemType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

data class BukkitItem(private val item: ItemStack) : Item {
    val meta by lazy { item.itemMeta }

    override var name: String?
        get() = meta.displayName
        set(value) { meta.displayName = value }
    override var lore: List<String>
        get() = meta.lore
        set(value) { meta.lore = value }
    // TODO - map to/from vanilla types, supporting new/old names
    override var type: ItemType
        get() = ItemType.of(item.type.name)
        set(value) { item.type = Material.matchMaterial(value.id) }
    override var amount: Int
        get() = item.amount
        set(value) { item.amount = value }
    override var durability: Short
        get() = item.durability
        set(value) { item.durability = value }
    override var data: Byte
        get() = item.data.data
        set(value) { item.data.data = value }
    override var visibility: Int
        get() = 0
        set(_) {}
    override var tags: Map<String, Any>
        get() = mapOf()
        set(_) {}
}