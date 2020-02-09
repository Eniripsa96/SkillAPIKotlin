package com.sucy.skill.config.category

import com.sucy.skill.api.values.Status
import com.sucy.skill.facade.api.data.effect.PotionType
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.match

class ClassificationSettings(config: Data) {
    val cleansablePotions = config.getStringList("Potions.Cleansable").map { PotionType.of(it) }.toSet()
    val purgablePotions = config.getStringList("Potions.Purgable").map { PotionType.of(it) }.toSet()
    val cleansableStatuses = config.getStringList("Statuses.Cleansable").mapNotNull { Status::class.match(it) }.toSet()
    val purgableStatuses = config.getStringList("Statuses.Purgable").mapNotNull { Status::class.match(it) }.toSet()
}