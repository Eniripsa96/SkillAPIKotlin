package com.sucy.skill.facade.bukkit.listeners

import com.sucy.skill.command.CommandNode
import com.sucy.skill.facade.bukkit.entity.BukkitCommandSender
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import com.sucy.skill.facade.bukkit.toActor
import net.minecraft.server.v1_13_R1.ServerCommand
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.server.ServerCommandEvent

data class CommandListener(val commands: CommandNode) : Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val args = event.message.split(" ").toMutableList()
        if (args[0].startsWith('/')) {
            args[0] = args[0].substring(1)
        }

        if (commands.execute(BukkitPlayer(event.player), args)) {
            event.isCancelled = true
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun onCommand(event: ServerCommandEvent) {
        val args = event.command.split(" ")
        if (commands.execute(BukkitCommandSender(event.sender), args)) {
            event.isCancelled = true
        }
    }
}