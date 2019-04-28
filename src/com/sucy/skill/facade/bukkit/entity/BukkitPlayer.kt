package com.sucy.skill.facade.bukkit.entity

import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.bukkit.data.inventory.BukkitPlayerInventory
import org.bukkit.entity.Player

/**
 * SkillAPIKotlin © 2018
 */
open class BukkitPlayer(override val entity: Player) : BukkitActor(entity), com.sucy.skill.facade.api.entity.Player {
    override val inventory: ActorInventory
        get() = BukkitPlayerInventory(entity.inventory)
    override var food: Double
        get() = entity.foodLevel.toDouble()
        set(value) { entity.foodLevel = value.toInt() }
    override var saturation: Double
        get() = entity.saturation.toDouble()
        set(value) { entity.saturation = value.toFloat() }
}