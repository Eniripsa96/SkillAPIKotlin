package com.sucy.skill.listener

import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.player.AsyncPlayerLoginEvent
import com.sucy.skill.facade.api.event.player.PlayerJoinEvent
import com.sucy.skill.facade.api.event.player.PlayerQuitEvent

/**
 * SkillAPIKotlin © 2018
 */
class MainListener : SkillAPIListener {

    override fun cleanup() {
        joinHandlers.clear()
    }

    @Listen(Step.REACT, true)
    fun onLogin(event: AsyncPlayerLoginEvent) {
    }

    @Listen
    fun onJoin(event: PlayerJoinEvent) {
        joinHandlers.forEach { it.invoke(event.player) }
    }

    @Listen
    fun onQuit(event: PlayerQuitEvent) {
        clearHandlers.forEach { it.invoke(event.player) }
    }

    companion object {
        val joinHandlers = ArrayList<(Player) -> Unit>()
        val clearHandlers = ArrayList<(Player) -> Unit>()
    }
}