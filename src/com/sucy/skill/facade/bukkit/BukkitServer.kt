package com.sucy.skill.facade.bukkit

import com.sucy.skill.command.CommandNode
import com.sucy.skill.command.CommandSender
import com.sucy.skill.facade.api.Server
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.bukkit.entity.BukkitActor
import com.sucy.skill.facade.bukkit.entity.BukkitCommandSender
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import com.sucy.skill.facade.bukkit.listeners.CommandListener
import com.sucy.skill.facade.bukkit.managers.BukkitPlayerManager
import com.sucy.skill.facade.bukkit.managers.BukkitTaskManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.plugin.SimplePluginManager

class BukkitServer(private val plugin: SkillAPIBukkit) : Server {
    override val players = BukkitPlayerManager()
    override val taskManager = BukkitTaskManager(plugin)
    override val plugins
        get() = Bukkit.getServer().pluginManager.plugins.asList()
    override fun getWorld(name: String) = BukkitWorld(Bukkit.getWorld(name))

    private var commandListener: CommandListener? = null

    override fun runCommand(user: Actor, command: String) {
        Bukkit.getServer().dispatchCommand(
                (user as BukkitActor).entity,
                command)
    }

    override fun registerCommands(commands: CommandNode) {
        var failed = false
        try {
            val commandField = SimplePluginManager::class.java.getDeclaredField("commandMap")
            if (!commandField.isAccessible) commandField.isAccessible = true
            val map: CommandMap = commandField.get(Bukkit.getPluginManager()) as CommandMap

            val knownField =  map.javaClass.getDeclaredField("knownCommands")
            if (!knownField.isAccessible) knownField.isAccessible = true
            val known: MutableMap<String, Command> = knownField.get(map) as MutableMap<String, Command>
            commandListener?.let {
                HandlerList.unregisterAll(it)
                it.commands.keys.forEach { key -> known.remove(key) }
            }
            commands.keys.forEach {
                map.register(it, BukkitCommand(commands, it))
            }
        } catch (ex: Exception) {
            failed = true
        }
        commandListener = CommandListener(commands)
        if (failed) Bukkit.getPluginManager().registerEvents(commandListener, plugin)
    }

    data class BukkitCommand(private val node: CommandNode, private val key: String) : Command(
            key,
            node.command?.description ?: "",
            "/${node.command?.path ?: key} ${node.command?.arguments ?: ""}",
            emptyList()) {

        override fun execute(sender: org.bukkit.command.CommandSender, label: String, args: Array<String>): Boolean {
            val internal: CommandSender = if (sender is Player) BukkitPlayer(sender) else BukkitCommandSender(sender)
            node.execute(internal, args.asList())
            return true
        }
    }
}