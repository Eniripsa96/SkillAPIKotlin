package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.world.BlockBreakEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class BlockBreakTrigger : Trigger<BlockBreakEvent>() {

    /** {@inheritDoc}  */
    override val key: String
        get() = "BLOCK_BREAK"

    /** {@inheritDoc}  */
    override val event: Class<BlockBreakEvent>
        get() = BlockBreakEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: BlockBreakEvent, level: Int): Boolean {
        val types = metadata.getStringList("material")
        return (types.isEmpty() || types.size == 1 && types.get(0).equals("ANY", true)
                || types.stream().anyMatch { mat -> event.block.type.equals(mat, true) })
    }

    /** {@inheritDoc}  */
    override fun setValues(event: BlockBreakEvent, data: MutableMap<String, Any>) {
        data["api-world-type"] = event.block.type
        data["api-world-loc"] = event.block.location
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: BlockBreakEvent): Actor? {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: BlockBreakEvent, settings: Data): Actor? {
        return event.actor
    }
}
