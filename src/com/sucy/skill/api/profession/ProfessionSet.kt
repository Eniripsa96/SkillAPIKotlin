package com.sucy.skill.api.profession

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.player.PlayerPreProfessionChangedEvent
import com.sucy.skill.facade.api.event.player.PlayerProfessionChangedEvent

class ProfessionSet : Iterable<ProfessionProgress> {
    private val professions = mutableMapOf<String, ProfessionProgress>()

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

    fun giveExp(player: Player, amount: Double, source: ExpSource, overrides: Map<String, Double>) {
        // TODO - level up events
        var leveledUp = false
        professions.values.forEach { leveledUp = it.giveExp(amount, source, overrides = emptyMap()) || leveledUp }
        if (leveledUp) updateStats(player)
    }

    fun giveExp(player: Player, amount: Double, group: String, overrides: Map<String, Double> = emptyMap()) {
        // TODO - level up events
        professions[group]?.let {
            if (it.forceGiveExp(amount, overrides)) {
                updateStats(player)
            }
        }
    }

    private fun setClassForGroup(player: Player, profession: Profession?, group: String): Boolean {
        val progress = get(group)
        val previous = progress?.data

        if (previous == profession) return false

        val event = PlayerPreProfessionChangedEvent(
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

        updateStats(player)

        SkillAPI.eventBus.trigger(PlayerProfessionChangedEvent(
                player,
                professions[group],
                previous,
                profession
        ))

        return true
    }

    fun updateStats(player: Player) {
        val (maxHealth, health) = resolve(player.maxHealth, player.health, SkillAPI.settings.classes.defaultHealth) { it.maxHealth }
        val (maxMana, mana) = resolve(player.maxMana, player.mana, SkillAPI.settings.classes.defaultMana) { it.maxMana }

        player.maxHealth = maxHealth
        player.maxMana = maxMana
        player.health = health
        player.mana = mana
    }

    private fun resolve(
            max: Double,
            current: Double,
            default: Int,
            getter: (ProfessionProgress) -> Double
    ): Pair<Double, Double> {
        val base = main?.let(getter) ?: default.toDouble()
        val otherProfessions = professions.values.filter { it != main }
        val total = base + otherProfessions.map(getter).sum()
        val diff = total - max

        return total to current + diff
    }

    override fun iterator() = all.iterator()
}