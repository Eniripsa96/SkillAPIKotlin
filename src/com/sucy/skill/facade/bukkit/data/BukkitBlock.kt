package com.sucy.skill.facade.bukkit.data

import com.sucy.skill.facade.api.data.Location
import org.bukkit.block.Block

/**
 * SkillAPIKotlin © 2018
 */
data class BukkitBlock(private val block: Block) : com.sucy.skill.facade.api.data.Block {
    override val location: Location = BukkitLocation(block.location)
    override val type: String
        get() = block.type.name

}