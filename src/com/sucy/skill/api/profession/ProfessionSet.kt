package com.sucy.skill.api.profession

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.player.PlayerClassChangeEvent
import com.sucy.skill.facade.api.event.player.PlayerPreClassChangeEvent

class ProfessionSet {
    private val professions = HashMap<String, ProfessionProgress>()

    val all: Collection<ProfessionProgress>
        get() = professions.values

    val main: ProfessionProgress?
        get() = this[SkillAPI.settings.account.mainGroup]

    operator fun get(group: String): ProfessionProgress? {
        return professions[group]
    }

    operator fun set(group: String, profession: ProfessionProgress) {
        professions[group] = profession
    }

    fun isProfessed(): Boolean {
        return !professions.isEmpty()
    }

    fun isProfessed(group: String): Boolean {
        return professions.containsKey(group)
    }

    fun canProfess(profession: Profession): Boolean {
        return if (profession.parents.isEmpty()) {
            !isProfessed(profession.group)
        } else {
            val current = get(profession.group)
            current != null && profession.parents.contains(current.data) && profession != current.data
        }
    }

    fun reset(player: Player, group: String): Boolean {
        return setClassForGroup(player, null, group)
    }

    fun profess(player: Player, profession: Profession): Boolean {
        return setClassForGroup(player, profession, profession.group)
    }

    private fun setClassForGroup(player: Player, profession: Profession?, group: String): Boolean {
        val progress = get(group)
        val previous = progress?.data

        if (previous == profession) return false

        val event = PlayerPreClassChangeEvent(
                player,
                progress,
                previous,
                profession
        )
        val modified = SkillAPI.eventBus.trigger(event)
        if (modified.cancelled) {
            return false
        }

        when {
            profession == null -> professions.remove(group)
            progress == null -> {
                val newProgress = ProfessionProgress(profession)
                newProgress.history = listOf(profession)
                professions[group] = newProgress
            }
            else -> {
                val newProgress = ProfessionProgress(profession)
                newProgress.history = progress.history.plus(progress.data)
                if (!SkillAPI.settings.forGroup(profession.group).resetLevel) {
                    newProgress.level = progress.level
                    newProgress.exp = progress.exp
                    newProgress.totalExp = progress.totalExp
                }
                professions[group] = newProgress
            }
        }

        SkillAPI.eventBus.trigger(PlayerClassChangeEvent(
                player,
                professions[group],
                previous,
                profession
        ))

        return true
    }
}