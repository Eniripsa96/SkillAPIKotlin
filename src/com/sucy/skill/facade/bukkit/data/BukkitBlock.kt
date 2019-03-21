package com.sucy.skill.facade.bukkit.data

import com.sucy.skill.facade.api.data.BlockState
import com.sucy.skill.facade.api.data.Location
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * SkillAPIKotlin © 2018
 */
data class BukkitBlock(private val block: Block) : com.sucy.skill.facade.api.data.Block {
    override val isSolid: Boolean
        get() = block.type.isSolid
    override val isAir: Boolean
        get() = block.type == Material.AIR
    override val state: BlockState
        get() = BukkitBlockState(block.state)
    override val location: Location = BukkitLocation(block.location)
    override val type: String
        get() = block.type.name

}