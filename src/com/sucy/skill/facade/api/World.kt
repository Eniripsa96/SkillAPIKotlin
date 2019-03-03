package com.sucy.skill.facade.api

import com.sucy.skill.facade.api.data.Block
import com.sucy.skill.facade.api.data.Vector3
import com.sucy.skill.facade.enums.Shape

interface World {
    fun getBlock(x: Int, y: Int, z: Int): Block

    fun getBlock(pos: Vector3): Block {
        return getBlock(pos.x.toInt(), pos.y.toInt(), pos.z.toInt())
    }

    fun forEachBlock(pos: Vector3, radX: Int, radY: Int, radZ: Int, shape: Shape, consumer: (Block) -> Unit) {
        val x = pos.x.toInt()
        val y = pos.y.toInt()
        val z = pos.z.toInt()

        return when (shape) {
            Shape.BOX -> {
                for (i in (x - radX)..(x + radX)) {
                    for (j in (y - radY)..(y + radY)) {
                        for (k in (z - radZ)..(z + radZ)) {
                            consumer(getBlock(i, j, k))
                        }
                    }
                }
            }
            Shape.SPHERE -> {

            }
        }
    }
}