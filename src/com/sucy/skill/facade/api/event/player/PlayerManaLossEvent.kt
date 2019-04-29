package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Player

data class PlayerManaLossEvent(
        val player: Player,
        val amount: Double,
        val reason: ManaCost,
        override var cancelled: Boolean = false
) : Event, Cancellable

enum class ManaCost {
    SKILL_CAST,
    SKILL_EFFECT,
    UNKNOWN
}