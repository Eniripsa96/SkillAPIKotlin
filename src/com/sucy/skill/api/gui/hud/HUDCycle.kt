package com.sucy.skill.api.gui.hud

import com.sucy.skill.facade.api.entity.Player

class HUDCycle<T : HUD>(
        private val views: List<T>,
        private val intervalTicks: Int
) : HUD {
    private var timer = 0
    private var index = 0

    init {
        require(intervalTicks > 0) { "Interval must be a positive number" }
    }

    override fun apply(player: Player) {
        if (views.isEmpty()) return

        timer++
        if (timer >= intervalTicks) {
            index = (index + 1) % views.size
            timer = 0
        }

        views[index].apply(player)
    }

    override fun hide(player: Player) {
        if (views.isEmpty()) return

        views[index].hide(player)
    }
}