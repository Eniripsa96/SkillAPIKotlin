package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.world.BlockPlaceEvent

/**
 * SkillAPI © 2018
 */
class BlockPlaceTrigger : Trigger<BlockPlaceEvent>() {

    /** {@inheritDoc}  */
    override val key: String
        get() = "BLOCK_PLACE"

    /** {@inheritDoc}  */
    override val event: Class<BlockPlaceEvent>
        get() = BlockPlaceEvent::class.java

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: BlockPlaceEvent, level: Int): Boolean {
        val types = metadata.getStringList("material")
        return (types.isEmpty() || types.size == 1 && types.get(0).equals("ANY", true)
                || types.stream().anyMatch { mat -> event.block.type.equals(mat, true) })
    }

    /** {@inheritDoc}  */
    override fun setValues(event: BlockPlaceEvent, target: Actor) {
        target.metadata["api-world-type"] = event.block.type
        target.metadata["api-world-loc"] = event.block.location
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: BlockPlaceEvent): Actor? {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: BlockPlaceEvent): Actor? {
        return event.actor
    }
}