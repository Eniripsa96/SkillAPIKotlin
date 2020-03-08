package com.sucy.skill.facade.api.data.block

import com.sucy.skill.facade.api.data.Location

/**
 * SkillAPIKotlin © 2018
 */
interface Block {
    val type: BlockType
    val location: Location
    val isSolid: Boolean
    val isAir: Boolean
    val lightLevel: Int

    val state: BlockState
}