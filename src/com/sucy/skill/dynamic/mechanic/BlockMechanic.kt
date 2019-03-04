package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.data.BlockState
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.managers.Task
import com.sucy.skill.facade.enums.Shape
import com.sucy.skill.util.math.toTicks

class BlockMechanic : Mechanic() {
    override val key = "block"

    private var air = false
    private var solid = true
    private var shape = Shape.BOX
    private var blockType = "dirt"
    private var blockData = 0

    override fun initialize() {
        super.initialize()

        casterOnce = false
        shape = Shape.valueOf(metadata.getString("shape", "box").toUpperCase())
        blockType = metadata.getString("blockType", blockType)
        blockData = metadata.getInt("blockData", blockData)

        val affected = metadata.getString("affectedBlocks", "solid").toLowerCase()
        air = affected != "ground"
        solid = affected != "air"
    }

    override fun execute(caster: Actor, level: Int, target: Actor, recipient: Actor): Boolean {
        val duration = compute("duration", caster, target).toTicks()
        val radX = compute("radX", caster, target).toInt()
        val radY = compute("radY", caster, target).toInt()
        val radZ = compute("radZ", caster, target).toInt()

        val world = SkillAPI.server.getWorld(recipient.location.world)
        val affected = ArrayList<Location>()
        world.forEachBlock(recipient.location.coords, radX, radY, radZ, shape) {
            if ((!solid || it.isSolid) && (!air || it.isAir)) {
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
        task = SkillAPI.server.taskManager.run(Runnable {
            affected.forEach {
                val remaining = pending.compute(it) { _, u -> u?.minus(1) } ?: 1
                if (remaining <= 0) {
                    original.remove(it)?.update(false)
                }
                tasks.remove(task)
            }
        }, duration)

        return !affected.isEmpty()
    }

    companion object {
        val tasks = HashSet<Task>()
        val pending = HashMap<Location, Int>()
        val original = HashMap<Location, BlockState>()
    }
}