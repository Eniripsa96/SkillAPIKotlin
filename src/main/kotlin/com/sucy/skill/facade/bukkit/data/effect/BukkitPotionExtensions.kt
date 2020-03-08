package com.sucy.skill.facade.bukkit.data.effect

import com.sucy.skill.facade.api.data.effect.PotionEffect
import com.sucy.skill.facade.api.data.effect.PotionType
import com.sucy.skill.facade.api.data.effect.VanillaPotionType
import org.bukkit.potion.PotionEffectType

fun PotionEffectType.internal(): PotionType {
    // TODO - proper mappings for old values
    return VanillaPotionType.valueOf(name)
}

fun PotionType.bukkit(): PotionEffectType {
    // TODO - proper mappings for old values
    return PotionEffectType.getByName(id)
}

fun PotionEffect.bukkit(): org.bukkit.potion.PotionEffect {
    return org.bukkit.potion.PotionEffect(
            type.bukkit(),
            durationTicks,
            tier
    )
}