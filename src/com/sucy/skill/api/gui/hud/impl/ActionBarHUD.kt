package com.sucy.skill.api.gui.hud.impl

import com.sucy.skill.api.gui.hud.HUD
import com.sucy.skill.api.gui.hud.HUDTextDisplay
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.util.server
import com.sucy.skill.util.text.color

class ActionBarHUD(
        private val displays: List<HUDTextDisplay>,
        private val separator: String = " &7|&r ".color(),
        private val prefix: String = "",
        private val suffix: String = ""
) : HUD {
    override fun apply(player: Player) {
        val content = displays.joinToString(
                separator = separator,
                prefix = prefix,
                postfix = suffix
        ) { it.text }

        server.showActionBar(player, content)
    }

    override fun hide(player: Player) {
        server.hideActionBar(player)
    }
}