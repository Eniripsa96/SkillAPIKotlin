package com.sucy.skill.api.gui.hud.view

import com.sucy.skill.api.gui.hud.HUD
import com.sucy.skill.api.gui.hud.NumberSource
import com.sucy.skill.facade.api.entity.Player

class ExpHUD(private val source: NumberSource) : HUD {
    override fun apply(player: Player) {
        player.enchantingExp = source.value(player) / source.maximum(player)
    }

    override fun hide(player: Player) {
        player.enchantingExp = 0.0
    }
}