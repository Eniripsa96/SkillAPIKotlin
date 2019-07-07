package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import com.sucy.skill.util.text.enumName
import kotlin.math.abs

class BlockCondition : Condition() {
    override val key = "block"

    private var types = setOf<String>()
    private var check = CheckType.ON_BLOCK

    override fun initialize() {
        super.initialize()

        types = metadata.getStringList("material", listOf()).map { it.enumName() }.toSet()
        check = CheckType::class.match(metadata.getString("standing", "on block"), CheckType.ON_BLOCK)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return when (check) {
            CheckType.IN_BLOCK -> isInBlock(recipient)
            CheckType.ON_BLOCK -> isOnBlock(recipient)
            CheckType.NOT_IN_BLOCK -> !isInBlock(recipient)
            CheckType.NOT_ON_BLOCK -> !isOnBlock(recipient)
        }
    }

    private fun isInBlock(actor: Actor): Boolean {
        val world = actor.world
        val loc = actor.location.coords
        val x = loc.x.toInt()
        val y = loc.y.toInt()
        val z = loc.z.toInt()

        var block = world.getBlock(x, y, z)
        if (block.isSolid) {
            block = world.getBlock(x, y + 1, z)
        }

        return types.contains(block.type.enumName())
    }

    private fun isOnBlock(actor: Actor): Boolean {
        if (actor.velocity.y != 0.0) return false

        val world = actor.world
        val loc = actor.location.coords
        val x = loc.x.toInt()
        val y = loc.y.toInt()
        val z = loc.z.toInt()

        // Since players can be crouching over an edge or standing on half blocks,
        // we need to check a few things in order:
        // 1. The block at the player's feet (for half blocks)
        // 2. The block beneath the player's feet (normal standing)
        // 3. The closest adjacent block (crouching over an edge)
        // 4. The next closest adjacent block (crouching over a corner)
        var block = world.getBlock(x, y, z)
        if (!block.isSolid) {
            block = world.getBlock(x, y - 1, z)
            if (!block.isSolid) {
                val dx = abs(0.5 - loc.x % 1)
                val dz = abs(0.5 - loc.z % 1)
                val o1 = getOffset(dx, loc.x) to getOffset(dz, loc.z)
                val o2 = if (dx > dz) {
                    o1.first to 0
                } else {
                    0 to o1.second
                }
                block = world.getBlock(x + o2.first, y, z + o2.second)
                if (!block.isSolid) {
                    block = world.getBlock(x + o1.first, y, z + o1.second)
                }
            }
        }

        return types.contains(block.type.enumName())
    }

    private fun getOffset(diff: Double, num: Double): Int {
        return if (diff >= 0.2) {
            if (num % 1 < 0.5) {
                -1
            } else {
                1
            }
        } else {
            0
        }
    }

    private enum class CheckType {
        IN_BLOCK,
        ON_BLOCK,
        NOT_IN_BLOCK,
        NOT_ON_BLOCK
    }
}