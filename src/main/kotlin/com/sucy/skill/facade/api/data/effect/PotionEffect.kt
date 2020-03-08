package com.sucy.skill.facade.api.data.effect

data class PotionEffect(
        val type: PotionType,
        val durationTicks: Int,
        val tier: Int
)