package com.sucy.skill.facade.bukkit.entity

import org.bukkit.command.CommandSender

data class BukkitCommandSender(val sender: CommandSender) : com.sucy.skill.command.CommandSender {
    override fun sendMessage(message: String) = sender.sendMessage(message)
    override fun hasPermission(permission: String) = sender.hasPermission(permission)
}