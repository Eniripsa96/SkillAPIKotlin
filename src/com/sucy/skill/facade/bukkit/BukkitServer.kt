package com.sucy.skill.facade.bukkit

import com.sucy.skill.command.CommandNode
import com.sucy.skill.command.CommandSender
import com.sucy.skill.facade.api.Server
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.bukkit.entity.BukkitActor
import com.sucy.skill.facade.bukkit.entity.BukkitCommandSender
import com.sucy.skill.facade.bukkit.entity.BukkitPlayer
import com.sucy.skill.facade.bukkit.listeners.CommandListener
import com.sucy.skill.facade.bukkit.managers.BukkitPlayerManager
import com.sucy.skill.facade.bukkit.managers.BukkitTaskManager
import com.sucy.skill.facade.bukkit.reflect.ActionBar
import com.sucy.skill.util.log.Logger
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.SimpleCommandMap
import org.bukkit.entity.LivingEntity
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
            commandField.isAccessible = true
            val map: CommandMap = commandField.get(Bukkit.getPluginManager()) as CommandMap

            val knownField = SimpleCommandMap::class.java.getDeclaredField("knownCommands")
            knownField.isAccessible = true
            val known: MutableMap<String, Command> = knownField.get(map) as MutableMap<String, Command>
            commandListener?.let {
                HandlerList.unregisterAll(it)
                it.commands.keys.forEach { key -> known.remove(key) }
            }
            commands.keys.forEach {
                map.register(it, BukkitCommand(commands, it))
            }
        } catch (ex: Exception) {
            Logger.warn("Unable to register commands directly - falling back to events")
            ex.printStackTrace()
            failed = true
        }
        commandListener = CommandListener(commands)
        if (failed) Bukkit.getPluginManager().registerEvents(commandListener, plugin)
    }

    override fun showActionBar(player: Player, content: String) {
        ActionBar.show((player as BukkitPlayer).entity, content)
    }

    override fun hideActionBar(player: Player) {
        ActionBar.show((player as BukkitPlayer).entity, "")
    }

    data class BukkitCommand(private val node: CommandNode, private val key: String) : Command(
            key,
            node.command?.description ?: "A SkillAPI provided command",
            "/${node.command?.path ?: key} ${node.command?.arguments ?: ""}",
            emptyList()) {

        override fun execute(sender: org.bukkit.command.CommandSender, label: String, args: Array<String>): Boolean {
            val internal: CommandSender = if (sender is LivingEntity) sender.toActor()!! else BukkitCommandSender(sender)
            return node.execute(internal, listOf(label) + args.asList())
        }
    }
}