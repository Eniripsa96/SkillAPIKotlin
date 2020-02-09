package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.config.category.ClassificationSettings

class PurgeMechanic : AbstractStatusMechanic() {
    override val key = "purge"
    override fun getAllPotions(settings: ClassificationSettings) = settings.purgablePotions
    override fun getAllStatuses(settings: ClassificationSettings) = settings.purgableStatuses
}