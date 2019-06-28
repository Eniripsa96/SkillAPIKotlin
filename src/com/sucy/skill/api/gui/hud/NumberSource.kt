package com.sucy.skill.api.gui.hud

import com.sucy.skill.facade.api.entity.Player

interface NumberSource {
    fun value(player: Player): Double
    fun maximum(player: Player): Double
}