package com.sucy.skill.api.gui.hud.source

import com.sucy.skill.api.gui.hud.NumberSource
import com.sucy.skill.facade.api.entity.Player

object LevelSource : NumberSource {
    override fun value(player: Player): Double {
        val professions = player.activeAccount.professionSet
        return professions.main?.level?.toDouble() ?: 0.0
    }

    override fun maximum(player: Player): Double {
        val professions = player.activeAccount.professionSet
        return professions.main?.data?.maxLevel?.toDouble() ?: 1.0
    }
}