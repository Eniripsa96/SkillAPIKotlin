package com.sucy.skill.facade.api.data.block

interface BlockState {
    var type: BlockType
    var data: Int

    fun update(applyPhysics: Boolean)
}