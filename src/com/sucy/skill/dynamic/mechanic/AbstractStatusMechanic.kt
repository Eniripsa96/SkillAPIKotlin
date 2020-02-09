package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.values.Status
import com.sucy.skill.config.category.ClassificationSettings
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.data.effect.PotionType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.match
import com.sucy.skill.util.math.formula.DynamicFormula

abstract class AbstractStatusMechanic : Mechanic() {
    private var potions: Set<PotionType> = emptySet()
    private var statuses: Set<Status> = emptySet()

    private lateinit var max: DynamicFormula

    override fun initialize() {
        super.initialize()
        max = metadata.getFormula("maxStatuses", 999.0)

        val potionList = metadata.getStringList("potions")
        val statusList = metadata.getStringList("statuses")

        potions = resolve(potionList, PotionType.Companion::of, this::getAllPotions)
        statuses = resolve(statusList, { Status::class.match(it) }, this::getAllStatuses)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val potionsToRemove = recipient.potionEffects.filter { potions.contains(it.type) }
        val statusesToRemove = statuses.filter { recipient.flags.isActive(it.name) }
        if (potionsToRemove.isEmpty() && statusesToRemove.isEmpty()) return false

        var toRemove = compute(max, context, target).toInt()
        potionsToRemove.forEach {
            recipient.removePotionEffect(it.type)
            if (--toRemove == 0) return true
        }

        statusesToRemove.forEach {
            recipient.flags.clear(it.name)
            if (--toRemove == 0) return true
        }

        return true
    }

    private fun <T : Any> resolve(list: List<String>, construct: (String) -> T?, fullSet: (ClassificationSettings) -> Set<T>): Set<T> {
        return if (list.any { it.equals("all", true) }) {
            fullSet(SkillAPI.settings.classification)
        } else {
            list.mapNotNull(construct).toSet()
        }
    }

    abstract fun getAllPotions(settings: ClassificationSettings): Set<PotionType>
    abstract fun getAllStatuses(settings: ClassificationSettings): Set<Status>
}