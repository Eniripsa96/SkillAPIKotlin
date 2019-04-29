package com.sucy.skill.facade.bukkit

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.bukkit.entity.BukkitActor
import com.sucy.skill.facade.bukkit.entity.BukkitEntity
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import com.sucy.skill.util.math.Vector3
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

val DEFAULT_MATERIAL = Material.JACK_O_LANTERN

fun Vector.wrap(): Vector3 {
    return Vector3(x, y, z)
}

fun Entity.toActor(): BukkitActor? {
    return when (this) {
        is Projectile -> shooter?.takeIf { it is LivingEntity }?.let { BukkitActor(it as LivingEntity) }
        is Player -> BukkitPlayer(this)
        is LivingEntity -> BukkitActor(this)
        else -> null
    }
}

fun com.sucy.skill.facade.api.entity.Entity.toBukkit(): Entity {
    return (this as BukkitEntity).entity
}

fun Item.toBukkit(): ItemStack {
    val material = Material.matchMaterial(type) ?: DEFAULT_MATERIAL
    val result = try {
        ItemStack(material, amount, durability, data)
    } catch (ex: Exception) {
        ItemStack(material, amount, durability)
    }

    val meta = result.itemMeta
    name?.let(meta::setDisplayName)
    meta.lore = lore

    return result
}

fun Vector3.toBukkit(): Vector {
    return Vector(x, y, z)
}

fun Entity.skillAPI(): BukkitEntity {
    return when(this) {
        is Player -> BukkitPlayer(this)
        is LivingEntity -> BukkitActor(this)
        else -> BukkitEntity(this)
    }
}

fun Location.asVector(): Vector3 {
    return Vector3(this.x, this.y, this.z)
}

fun Material.isHelmet(): Boolean {
    return name.endsWith("_HELMET") || name == "TURTLE_SHELL"
}

fun Material.isChestplate(): Boolean {
    return name.endsWith("_CHESTPLATE")
}

fun Material.isLeggings(): Boolean {
    return name.endsWith("_LEGGINGS")
}

fun Material.isBoots(): Boolean {
    return name.endsWith("_BOOTS")
}