package com.sucy.skill.facade.bukkit

import com.sucy.skill.facade.api.World
import com.sucy.skill.facade.api.data.Weather
import com.sucy.skill.facade.api.data.block.Block
import com.sucy.skill.facade.api.data.block.BlockType
import com.sucy.skill.facade.api.data.effect.Color
import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.data.inventory.ItemType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.bukkit.data.block.BukkitBlock
import com.sucy.skill.facade.bukkit.util.BukkitConversion
import com.sucy.skill.util.match
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.sq
import com.sucy.skill.util.math.toChunk
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import xyz.xenondevs.particle.ParticleEffect
import xyz.xenondevs.particle.data.ParticleData
import xyz.xenondevs.particle.data.color.NoteColor
import xyz.xenondevs.particle.data.color.RegularColor
import xyz.xenondevs.particle.data.texture.BlockTexture
import xyz.xenondevs.particle.data.texture.ItemTexture
import com.sucy.skill.facade.api.data.Location as ILocation
import com.sucy.skill.facade.api.data.effect.ParticleData as IParticleData

class BukkitWorld(private val world: org.bukkit.World) : World {
    override val name: String
        get() = world.name
    override var time: Long
        get() = world.time
        set(value) {
            world.time = value
        }

    override fun isLoaded(location: ILocation): Boolean {
        val coords = location.coords
        return Bukkit.getWorld(location.world).isChunkLoaded(coords.x.toChunk(), coords.z.toChunk())
    }

    override fun getActorsInRadius(center: Vector3, radius: Double): List<Actor> {
        if (radius <= 0) return emptyList()

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

    override fun playParticle(
            particleData: IParticleData,
            location: ILocation,
            players: List<Player>
    ) {
        val effect = ParticleEffect::class.match(particleData.particle.id, ParticleEffect.CRIT)
        val effectData: ParticleData? = when (val data = particleData.data) {
            is ItemType -> ItemTexture(ItemStack(BukkitConversion.convertToMaterial(data)))
            is BlockType -> BlockTexture(BukkitConversion.convertToMaterial(data))
            is Item -> ItemTexture(data.toBukkit())
            is Block -> BlockTexture(BukkitConversion.convertToMaterial(data.type))
            is Color -> if (effect == ParticleEffect.NOTE) {
                NoteColor(data.red / 10)
            } else {
                RegularColor(data.red, data.green, data.blue)
            }
            else -> null
        }

        effect.display(
                location.toBukkit(),
                particleData.offset.toBukkit(),
                particleData.speed,
                particleData.amount,
                effectData,
                players.map { it.toBukkit() as org.bukkit.entity.Player }
        )
    }
}