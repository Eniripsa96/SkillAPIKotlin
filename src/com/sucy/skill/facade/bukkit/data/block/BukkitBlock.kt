package com.sucy.skill.facade.bukkit.data.block

import com.sucy.skill.facade.api.data.block.BlockState
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.data.block.BlockType
import com.sucy.skill.facade.api.data.block.UnknownBlockType
import com.sucy.skill.facade.bukkit.data.BukkitLocation
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * SkillAPIKotlin © 2018
 */
data class BukkitBlock(private val block: Block) : com.sucy.skill.facade.api.data.block.Block {
    override val isSolid: Boolean
        get() = block.type.isSolid
    override val isAir: Boolean
        get() = block.type == Material.AIR
    override val state: BlockState
        get() = BukkitBlockState(block.state)
    override val location: Location = BukkitLocation(block.location)
    override val type: BlockType
        get() = UnknownBlockType(block.type.name)
    override val lightLevel: Int
        get() = block.lightLevel.toInt()
}