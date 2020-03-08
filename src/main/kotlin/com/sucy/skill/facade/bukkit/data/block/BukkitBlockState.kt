package com.sucy.skill.facade.bukkit.data.block

import com.sucy.skill.facade.api.data.block.BlockType
import com.sucy.skill.facade.bukkit.util.BukkitConversion
import org.bukkit.Material
import org.bukkit.block.BlockState

data class BukkitBlockState(private val state: BlockState) : com.sucy.skill.facade.api.data.block.BlockState {
    override var type: BlockType
        get() = BukkitConversion.convertToBlock(state.type)
        set(value) { state.type = BukkitConversion.convertToMaterial(value) }
    override var data: Int
        get() = state.data.data.toInt()
        set(value) { state.data.data = value.toByte() }

    override fun update(applyPhysics: Boolean) {
        state.update(applyPhysics)
    }
}