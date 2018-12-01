package com.sucy.skill.api

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * SkillAPIKotlin © 2018
 */
class Icon() {
    var icon: ItemStack = ItemStack(Material.PUMPKIN)
    var lore: List<String> = ArrayList()
    var permission: String? = null
}