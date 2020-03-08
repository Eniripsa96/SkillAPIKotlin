package com.sucy.skill.api.gui.hud.source

import com.sucy.skill.api.gui.hud.NumberSource
import com.sucy.skill.facade.api.entity.Player

object ManaSource : NumberSource {
    override fun value(player: Player): Double = player.mana
    override fun maximum(player: Player): Double = player.maxMana
}