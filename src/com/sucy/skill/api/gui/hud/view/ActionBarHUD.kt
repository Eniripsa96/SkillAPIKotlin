package com.sucy.skill.api.gui.hud.view

import com.sucy.skill.api.gui.hud.HUD
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.util.server
import com.sucy.skill.util.text.DynamicFilter
import com.sucy.skill.util.text.context.ActorFilterContext

class ActionBarHUD(
        private val display: String
) : HUD {
    override fun apply(player: Player) {
        val content = DynamicFilter.apply(display, ActorFilterContext("player", player))
        server.showActionBar(player, content)
    }

    override fun hide(player: Player) {
        server.hideActionBar(player)
    }
}