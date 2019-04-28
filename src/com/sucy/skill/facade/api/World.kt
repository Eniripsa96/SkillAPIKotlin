package com.sucy.skill.facade.api

import com.sucy.skill.facade.api.data.Block
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.enums.Shape
import com.sucy.skill.util.math.Vector3

interface World {
    fun getBlock(x: Int, y: Int, z: Int): Block
    fun getActorsInRadius(center: Vector3, radius: Double): List<Actor>
    fun createExplosion(pos: Vector3, power: Double = 2.0, fire: Boolean = false, damageBlocks: Boolean = false)
    fun isLoaded(location: Location): Boolean

    fun getBlock(pos: Vector3): Block {
        return getBlock(pos.x.toInt(), pos.y.toInt(), pos.z.toInt())
    }

    fun forEachChunkPos(pos: Vector3, radius: Double, handler: (x: Int, z: Int) -> Unit) {
        val minX = (pos.x - radius).toInt() shr 4
        val maxX = (pos.x + radius).toInt() shr 4
        val minZ = (pos.z - radius).toInt() shr 4
        val maxZ = (pos.z + radius).toInt() shr 4

        for (x in minX..maxX) {
            for (z in minZ..maxZ) {
                handler.invoke(x, z)
            }
        }
    }

    fun forEachBlock(pos: Vector3, radX: Int, radY: Int, radZ: Int, shape: Shape, consumer: (Block) -> Unit) {
        val x = pos.x.toInt()
        val y = pos.y.toInt()
        val z = pos.z.toInt()

        return when (shape) {
            Shape.BOX -> iterate(x, y, z, radX, radY, radZ) { i, j, k ->
                consumer.invoke(getBlock(i, j, k))
            }
            Shape.SPHERE -> {
                iterate(x, y, z, radX, radY, radZ) { i, j, k ->
                    val dx = (x - i) / radX
                    val dy = (y - j) / radY
                    val dz = (z - k) / radZ

                    if (dx * dx + dy * dy + dz * dz < 1) {
                        consumer.invoke(getBlock(i, j, k))
                    }
                }
            }
        }
    }

    private inline fun iterate(x: Int, y: Int, z: Int, radX: Int, radY: Int, radZ: Int, handler: (Int, Int, Int) -> Unit) {
        for (i in (x - radX)..(x + radX)) {
            for (j in (y - radY)..(y + radY)) {
                for (k in (z - radZ)..(z + radZ)) {
                    handler.invoke(i, j, k)
                }
            }
        }
    }
}