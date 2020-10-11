package com.sucy.skill.facade.bukkit.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.data.GameMode
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.bukkit.data.inventory.BukkitPlayerInventory
import com.sucy.skill.util.match
import com.sucy.skill.util.math.limit
import org.bukkit.entity.Player

/**
 * SkillAPIKotlin © 2018
 */
open class BukkitPlayer(override val entity: Player) : BukkitActor(entity), com.sucy.skill.facade.api.entity.Player {
    override var enchantingLevel: Int
        get() = entity.level
        set(value) {
            entity.level = value
        }
    override var enchantingExp: Double
        get() = entity.exp.toDouble()
        set(value) {
            entity.exp = limit(value, 0.0, 0.999).toFloat()
        }
    override var gameMode: GameMode
        get() = GameMode::class.match(entity.gameMode.name, GameMode.SURVIVAL)
        set(value) {
            entity.gameMode = org.bukkit.GameMode::class.match(value.name, org.bukkit.GameMode.SURVIVAL)
        }
    override val inventory: ActorInventory
        get() = BukkitPlayerInventory(entity.inventory)
    override var food: Double
        get() = entity.foodLevel.toDouble()
        set(value) {
            entity.foodLevel = limit(value, 0.0, 20.0).toInt()
        }
    override var saturation: Double
        get() = entity.saturation.toDouble()
        set(value) {
            entity.saturation = limit(value, 0.0, food).toFloat()
        }
    override val isCrouching: Boolean
        get() = entity.isSneaking
    override val isOnline: Boolean
        get() = entity.isOnline
}