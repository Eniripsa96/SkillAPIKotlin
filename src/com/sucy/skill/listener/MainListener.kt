package com.sucy.skill.listener

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.player.AsyncPlayerLoginEvent
import com.sucy.skill.facade.api.event.player.PlayerJoinEvent

/**
 * SkillAPIKotlin © 2018
 */
class MainListener : SkillAPIListener {

    override fun cleanup() {
        joinHandlers.clear()
    }

    @Listen(Step.REACT, true)
    fun onLogin(event: AsyncPlayerLoginEvent) {
        if (SkillAPI.settings.saving.enableSqlDatabase && SkillAPI.settings.saving.sqlLoadDelayInSeconds > 0) {
            // temp
        } else {
            // load
        }
    }

    @Listen
    fun onJoin(event: PlayerJoinEvent) {

    }

    fun init(player: Player) {

    }

    companion object {
        val joinHandlers = ArrayList<(Player) -> Unit>()
        val clearHandlers = ArrayList<(Player) -> Unit>()
    }
}