package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Player

data class PlayerRespawnEvent(
        val player: Player,
        var spawnLocation: Location,
        val isBedSpawn: Boolean
) : Event