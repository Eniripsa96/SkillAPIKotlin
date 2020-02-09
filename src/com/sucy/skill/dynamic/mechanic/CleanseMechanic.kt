package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.config.category.ClassificationSettings

class CleanseMechanic : AbstractStatusMechanic() {
    override val key = "cleanse"
    override fun getAllPotions(settings: ClassificationSettings) = settings.cleansablePotions
    override fun getAllStatuses(settings: ClassificationSettings) = settings.cleansableStatuses
}