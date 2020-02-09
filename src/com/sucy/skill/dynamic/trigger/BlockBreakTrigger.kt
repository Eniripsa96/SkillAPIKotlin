package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.world.BlockBreakEvent

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
        return (types.isEmpty() || types.size == 1 && types[0].equals("ANY", true)
                || types.stream().anyMatch { mat -> event.block.type.id.equals(mat, true) })
    }

    /** {@inheritDoc}  */
    override fun setValues(event: BlockBreakEvent, target: Actor) {
        target.metadata["api-world-type"] = event.block.type
        target.metadata["api-world-loc"] = event.block.location
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: BlockBreakEvent): Actor? {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: BlockBreakEvent): Actor? {
        return event.actor
    }
}
