package com.sucy.skill.api.gui.hud.source

import com.sucy.skill.api.gui.hud.NumberSource
import com.sucy.skill.facade.api.entity.Player

object ExpSource : NumberSource {
    override fun value(player: Player): Double {
        val professions = player.activeAccount.professionSet
        return professions.main?.exp ?: 0.0
    }

    override fun maximum(player: Player): Double {
        val professions = player.activeAccount.professionSet
        return professions.main?.requiredExp ?: 1.0
    }
}