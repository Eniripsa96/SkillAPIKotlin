package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Player

data class PlayerManaGainEvent(
        val player: Player,
        val amount: Double,
        val reason: ManaSource,
        override var cancelled: Boolean = false
) : Event, Cancellable

enum class ManaSource {
    REGEN,
    SKILL_EFFECT,
    COMMAND,
    UNKNOWN
}
