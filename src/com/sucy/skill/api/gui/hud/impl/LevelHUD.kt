package com.sucy.skill.api.gui.hud.impl

import com.sucy.skill.api.gui.hud.HUD
import com.sucy.skill.api.gui.hud.HUDNumberDisplay
import com.sucy.skill.facade.api.entity.Player

class LevelHUD(private val display: HUDNumberDisplay) : HUD {
    override fun apply(player: Player) {
        player.enchantingLevel = display.value.toInt()
    }

    override fun hide(player: Player) {
        player.enchantingLevel = 0
    }
}