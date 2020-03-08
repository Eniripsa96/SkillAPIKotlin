package com.sucy.skill.api.values

enum class Status {
    ABSORB,
    CHANNEL,
    DISARM,
    INVINCIBLE,
    ROOT,
    SILENCE,
    STUN;

    val flag = name.toLowerCase()
}