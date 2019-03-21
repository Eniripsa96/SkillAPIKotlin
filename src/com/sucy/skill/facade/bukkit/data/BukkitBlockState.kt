package com.sucy.skill.facade.bukkit.data

import org.bukkit.Material
import org.bukkit.block.BlockState

data class BukkitBlockState(private val state: BlockState) : com.sucy.skill.facade.api.data.BlockState {
    override var type: String
        get() = state.type.name
        set(value) { state.type = Material.matchMaterial(value) }
    override var data: Int
        get() = state.data.data.toInt()
        set(value) { state.data.data = value.toByte() }

    override fun update(applyPhysics: Boolean) {
        state.update(applyPhysics)
    }
}