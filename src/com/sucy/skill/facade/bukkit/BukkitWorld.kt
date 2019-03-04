package com.sucy.skill.facade.bukkit

import com.sucy.skill.facade.api.World
import com.sucy.skill.facade.api.data.Block
import com.sucy.skill.facade.bukkit.data.BukkitBlock

class BukkitWorld(private val world: org.bukkit.World) : World {
    override fun getBlock(x: Int, y: Int, z: Int): Block {
        return BukkitBlock(world.getBlockAt(x, y, z))
    }
}