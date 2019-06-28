package com.sucy.skill.api.gui.hud.view

import com.sucy.skill.api.gui.hud.HUD
import com.sucy.skill.api.gui.hud.NumberSource
import com.sucy.skill.facade.api.entity.Player

class LevelHUD(private val source: NumberSource) : HUD {
    override fun apply(player: Player) {
        player.enchantingLevel = source.value(player).toInt()
    }

    override fun hide(player: Player) {
        player.enchantingLevel = 0
    }
}