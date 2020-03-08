package com.sucy.skill.facade.api.data.effect

import com.sucy.skill.util.match

interface Particle {
    val id: String

    companion object {
        fun of(name: String): Particle = VanillaParticle::class.match(name) ?: UnknownParticle(name)
    }
}

data class UnknownParticle(override val id: String) : Particle

enum class VanillaParticle : Particle {
    BARRIER,
    BLOCK_CRACK,
    BLOCK_DUST,
    BUBBLE_COLUMN_UP,
    BUBBLE_POP,
    CAMPFIRE_COSY_SMOKE,
    CAMPFIRE_SIGNAL_SMOKE,
    CLOUD,
    COMPOSTER,
    CRIT,
    CRIT_MAGIC,
    CURRENT_DOWN,
    DAMAGE_INDICATOR,
    DOLPHIN,
    DRAGON_BREATH,
    DRIP_LAVA,
    DRIP_WATER,
    DRIPPING_HONEY,
    ENCHANTMENT_TABLE,
    END_ROD,
    EXPLOSION_HUGE,
    EXPLOSION_LARGE,
    EXPLOSION_NORMAL,
    FALLING_DUST,
    FALLING_HONEY,
    FALLING_NECTAR,
    FIREWORKS_SPARK,
    FLAME,
    FOOTSTEP,
    HEART,
    ITEM_CRACK,
    LANDING_HONEY,
    LAVA,
    MOB_APPEARANCE,
    NAUTILUS,
    NOTE,
    PORTAL,
    REDSTONE,
    SLIME,
    SMOKE_LARGE,
    SMOKE_NORMAL,
    SNEEZE,
    SNOWBALL,
    SNOW_SHOVEL,
    SPELL,
    SPELL_INSTANT,
    SPELL_MOB,
    SPELL_MOB_AMBIENT,
    SPELL_WITCH,
    SPIT,
    SQUID_INK,
    SUSPENDED,
    SUSPENDED_DEPTH,
    SWEEP_ATTACK,
    TOTEM,
    TOWN_AURA,
    VILLAGER_ANGRY,
    VILLAGER_HAPPY,
    WATER_BUBBLE,
    WATER_DROP,
    WATER_SPLASH,
    WATER_WAKE;

    override val id = name
}