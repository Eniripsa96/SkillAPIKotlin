package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.api.profession.Profession
import com.sucy.skill.api.profession.ProfessionProgress
import com.sucy.skill.facade.api.entity.Player

data class PlayerPreClassChangeEvent(
        val player: Player,
        val progress: ProfessionProgress?,
        val from: Profession?,
        val to: Profession?,
        override var cancelled: Boolean = false
) : Event, Cancellable {
    init {
        require(from != to) { "Class change events should only be triggered with a change" }
    }
}