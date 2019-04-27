package com.sucy.skill.facade.bukkit

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.bukkit.BukkitUtil.wrap
import com.sucy.skill.facade.bukkit.entity.BukkitActor
import com.sucy.skill.facade.bukkit.entity.BukkitEntity
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import com.sucy.skill.util.math.Vector3
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

/**
 * SkillAPIKotlin © 2018
 */
object BukkitUtil {
    val DEFAULT_MATERIAL = Material.JACK_O_LANTERN

    fun wrap(vector: Vector): Vector3 {
        return Vector3(vector.x, vector.y, vector.z)
    }

    fun wrap(entity: Entity): BukkitEntity {
        return when (entity) {
            is Player -> BukkitPlayer(entity)
            is LivingEntity -> BukkitActor(entity)
            else -> BukkitEntity(entity)
        }
    }

    fun findActor(entity: Entity): BukkitActor? {
        return when (entity) {
            is Projectile -> entity.shooter?.takeIf { it is LivingEntity }?.let { BukkitActor(it as LivingEntity) }
            is Player -> BukkitPlayer(entity)
            is LivingEntity -> BukkitActor(entity)
            else -> null
        }
    }

    fun toBukkit(entity: com.sucy.skill.facade.api.entity.Entity): Entity {
        return (entity as BukkitEntity).entity
    }

    fun toBukkit(item: Item): ItemStack {
        val material = Material.matchMaterial(item.type) ?: DEFAULT_MATERIAL
        val result = try {
            ItemStack(material, item.amount, item.durability, item.data)
        } catch (ex: Exception) {
            ItemStack(material, item.amount, item.durability)
        }

        val meta = result.itemMeta
        item.name?.let(meta::setDisplayName)
        meta.lore = item.lore

        return result
    }
}

fun Entity.skillAPI(): BukkitEntity {
    return wrap(this)
}

fun Location.asVector(): Vector3 {
    return Vector3(this.x, this.y, this.z)
}