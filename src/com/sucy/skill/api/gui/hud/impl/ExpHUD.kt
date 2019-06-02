package com.sucy.skill.api.gui.hud.impl

import com.sucy.skill.api.gui.hud.HUD
import com.sucy.skill.api.gui.hud.HUDNumberDisplay
import com.sucy.skill.facade.api.entity.Player

class ExpHUD(private val display: HUDNumberDisplay) : HUD {
    override fun apply(player: Player) {
        player.enchantingExp = display.value / display.maximum
    }

    override fun hide(player: Player) {
        player.enchantingExp = 0.0
    }
}