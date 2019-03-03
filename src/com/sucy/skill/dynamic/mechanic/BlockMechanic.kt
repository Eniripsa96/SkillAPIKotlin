package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.enums.Shape

class BlockMechanic : Mechanic() {
    override val key = "block"

    private var radX = 2
    private var radY = 2
    private var radZ = 2
    private var air = false
    private var solid = true
    private var shape = Shape.BOX
    private var blockType = "dirt"
    private var blockData = 0

    override fun initialize() {
        super.initialize()

        casterOnce = false
        radX = metadata.getDouble("radX", 2.0).toInt()
        radY = metadata.getDouble("radY", 2.0).toInt()
        radZ = metadata.getDouble("radZ", 2.0).toInt()
        shape = Shape.valueOf(metadata.getString("shape", "box").toUpperCase())
        blockType = metadata.getString("")

        val affected = metadata.getString("affectedBlocks", "solid").toLowerCase()
        air = affected != "ground"
        solid = affected != "air"
    }

    override fun execute(caster: Actor, level: Int, target: Actor, recipient: Actor): Boolean {
        val world = SkillAPI.server.getWorld(recipient.location.world)
        world.forEachBlock(recipient.location.coords, radX, radY, radZ, shape) {
            if ((!solid || it.isSolid) && (!air || it.isAir)) {
                if (pending.containsKey(it.location)) {
                    pending[it.location] = pending[it.location]!! + 1
                } else {
                    pending[it.location] = 1
                    original[it.location] = it.type
                }

                val state = it.state
                state.type = blockType
                state.data = blockData
                state.update(false)
            }
        }
    }

    companion object {
        val pending = HashMap<Location, Int>()
        val original = HashMap<Location, String>()
    }
}