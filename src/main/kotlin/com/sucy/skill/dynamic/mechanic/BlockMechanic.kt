package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.data.block.BlockState
import com.sucy.skill.facade.api.data.block.BlockType
import com.sucy.skill.facade.api.data.block.VanillaBlockType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.managers.Task
import com.sucy.skill.facade.enums.Shape
import com.sucy.skill.util.match
import com.sucy.skill.util.math.toTicks

class BlockMechanic : Mechanic() {
    override val key = "block"

    private var air = false
    private var solid = true
    private var shape = Shape.BOX
    private var blockType: BlockType = VanillaBlockType.DIRT
    private var blockData = 0

    override fun initialize() {
        super.initialize()

        casterOnce = false
        shape = Shape::class.match(metadata.getString("shape", "box"), Shape.BOX)
        blockType = metadata.getString("blockType")?.let { BlockType.of(it) } ?: blockType
        blockData = metadata.getInt("blockData", blockData)

        val affected = metadata.getString("affectedBlocks", "solid").toLowerCase()
        air = affected != "ground"
        solid = affected != "air"
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val duration = compute("duration", context, target).toTicks()
        val radX = compute("radX", context, target).toInt()
        val radY = compute("radY", context, target).toInt()
        val radZ = compute("radZ", context, target).toInt()

        val world = SkillAPI.server.getWorld(recipient.location.world)
        val affected = mutableListOf<Location>()
        world.forEachBlock(recipient.location.coords, radX, radY, radZ, shape) {
            if ((solid || !it.isSolid) && (air || !it.isAir)) {
                if (pending.containsKey(it.location)) {
                    pending[it.location] = pending[it.location]!! + 1
                } else {
                    pending[it.location] = 1
                    original[it.location] = it.state
                }

                val state = it.state
                state.type = blockType
                state.data = blockData
                state.update(false)

                affected.add(recipient.location)
            }
        }

        var task: Task? = null
        task = SkillAPI.server.taskManager.run(duration) {
            affected.forEach {
                val remaining = pending.compute(it) { _, u -> u?.minus(1) } ?: 1
                if (remaining <= 0) {
                    original.remove(it)?.update(false)
                }
                tasks.remove(task)
            }
        }

        return affected.isNotEmpty()
    }

    companion object {
        val tasks = HashSet<Task>()
        val pending = mutableMapOf<Location, Int>()
        val original = mutableMapOf<Location, BlockState>()
    }
}