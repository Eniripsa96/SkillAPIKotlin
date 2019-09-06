package com.sucy.skill.facade.api.data.effect

interface PotionType {
    val id: String
}

data class UnknownPotionType(override val id: String) : PotionType

enum class VanillaPotionType : PotionType {
    SPEED,
    SLOW,
    FAST_DIGGING,
    SLOW_DIGGING,
    INCREASE_DAMAGE,
    HEAL,
    HARM,
    JUMP,
    CONFUSION,
    REGENERATION,
    DAMAGE_RESISTANCE,
    FIRE_RESISTANCE,
    WATER_BREATHING,
    INVISIBILITY,
    BLINDNESS,
    NIGHT_VISION,
    HUNGER,
    WEAKNESS,
    POISON,
    WITHER,
    HEALTH_BOOST,
    ABSORPTION,
    SATURATION,
    GLOWING,
    LEVITATION,
    LUCK,
    UNLUCK,
    SLOW_FALLING,
    CONDUIT_POWER,
    DOLPHINS_GRACE;

    override val id = name
}