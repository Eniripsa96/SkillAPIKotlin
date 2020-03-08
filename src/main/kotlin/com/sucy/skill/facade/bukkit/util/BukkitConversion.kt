package com.sucy.skill.facade.bukkit.util

import com.sucy.skill.facade.api.data.block.BlockType
import com.sucy.skill.facade.api.data.block.VanillaBlockType
import com.sucy.skill.facade.api.data.inventory.ItemType
import com.sucy.skill.facade.api.data.inventory.VanillaItemType
import org.bukkit.Material

object BukkitConversion {
    // TODO - build out maps for each version
    fun convertToItem(material: Material): ItemType = VanillaItemType.valueOf(material.name)
    fun convertToBlock(material: Material): BlockType = VanillaBlockType.valueOf(material.name)
    fun convertToMaterial(itemType: ItemType): Material = Material.matchMaterial(itemType.id)
    fun convertToMaterial(blockType: BlockType): Material = Material.matchMaterial(blockType.id)
}