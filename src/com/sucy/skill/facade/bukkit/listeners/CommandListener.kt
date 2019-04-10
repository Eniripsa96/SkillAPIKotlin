package com.sucy.skill.facade.bukkit.listeners

import com.sucy.skill.command.CommandNode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

data class CommandListener(val commands: CommandNode) : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val args = event.message.split(" ")

    }
}