package com.sucy.skill.facade.bukkit

import com.sucy.skill.facade.api.World
import com.sucy.skill.facade.api.data.Weather
import com.sucy.skill.facade.api.data.block.Block
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.bukkit.data.block.BukkitBlock
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.sq
import com.sucy.skill.util.math.toChunk
import org.bukkit.Bukkit
import org.bukkit.Location

class BukkitWorld(private val world: org.bukkit.World) : World {
    override val name: String
        get() = world.name
    override var time: Long
        get() = world.time
        set(value) {
            world.time = value
        }

    override fun isLoaded(location: com.sucy.skill.facade.api.data.Location): Boolean {
        val coords = location.coords
        return Bukkit.getWorld(location.world).isChunkLoaded(coords.x.toChunk(), coords.z.toChunk())
    }

    override fun getActorsInRadius(center: Vector3, radius: Double): List<Actor> {
        val rSq = sq(radius)
        val result = mutableListOf<Actor>()
        forEachChunkPos(center, radius) { i, j ->
            world.getChunkAt(i, j).entities.forEach {
                val actor = it.toActor()
                if (actor != null && actor.dSq(center) <= rSq) {
                    result.add(actor)
                }
            }
        }
        return result
    }

    override fun createExplosion(pos: Vector3, power: Double, fire: Boolean, damageBlocks: Boolean) {
        world.createExplosion(pos.x, pos.y, pos.z, power.toFloat(), fire, damageBlocks)
    }

    override fun strikeLightning(pos: Vector3) {
        world.strikeLightning(Location(world, pos.x, pos.y, pos.z))
    }

    override fun playLightningEffect(pos: Vector3) {
        world.strikeLightningEffect(Location(world, pos.x, pos.y, pos.z))
    }

    override fun getBlock(x: Int, y: Int, z: Int): Block {
        return BukkitBlock(world.getBlockAt(x, y, z))
    }

    override fun getBiome(x: Int, y: Int, z: Int): String {
        return world.getBiome(x, z).name
    }

    override fun getTemperature(x: Int, y: Int, z: Int): Double {
        return world.getBlockAt(x, y, z).temperature
    }

    override fun getWeather(x: Int, y: Int, z: Int): Weather {
        return when {
            world.isThundering -> Weather.THUNDERING
            world.hasStorm() -> Weather.RAINING
            else -> Weather.NONE
        }
    }
}