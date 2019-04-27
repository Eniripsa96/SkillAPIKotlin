package com.sucy.skill.facade.bukkit.data

import com.sucy.skill.facade.api.data.Item
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
    override var type: String
        get() = item.type.name
        set(value) { item.type = Material.matchMaterial(value) }
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
        set(value) {}
    override var tags: Map<String, Any>
        get() = mapOf()
        set(value) {}
}