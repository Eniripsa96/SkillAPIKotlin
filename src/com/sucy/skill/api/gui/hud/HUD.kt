package com.sucy.skill.api.gui.hud

import com.sucy.skill.facade.api.entity.Player

interface HUD {
    fun apply(player: Player)
    fun hide(player: Player)
    fun appliesTo(player: Player): Boolean = true
}