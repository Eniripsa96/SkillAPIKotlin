package com.sucy.skill.command

import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.util.log.Logger

/**
 * Handles building out relations between commands and handles delegating between them
 */
class CommandNode {
    private val subNodes = mutableMapOf<String, CommandNode>()

    var command: ConfigurableCommand? = null
        private set

    val keys: Set<String>
        get() = subNodes.keys

    fun execute(sender: CommandSender, args: List<String>): Boolean {
        val first = args.first()
        val root = if (first.startsWith("/")) first.substring(1) else first
        return subNodes[root]?.execute(sender, args,1) ?: false
    }

    fun execute(sender: CommandSender, args: List<String>, index: Int): Boolean {
        require(index >= 0) { "Index should not be negative" }
        require(index <= args.size) { "Index should not be larger than the number of arguments" }

        val node = if (index < args.size) subNodes[args[index]] else null

        if (node == null) {
            command?.let { cmd ->
                val hasPermission = cmd.permission?.let { sender.hasPermission(it) } ?: true
                if (cmd.condition(sender) && hasPermission) {
                    val input = args.subList(index, args.size)
                    if (cmd.logic.argumentPattern.hasEnoughArguments(input)) {
                        cmd.logic.execute(sender, cmd.logic.argumentPattern.parse(input))
                    } else {
                        // TODO - display usage
                    }
                    return true
                } else if (sender is Player) {
                    Logger.info("${sender.name} cannot execute command ${args.joinToString(" ")}")
                }
            }
            return false
        } else {
            return node.execute(sender, args, index + 1)
        }
    }

    fun merge(command: ConfigurableCommand) {
        val args = command.path.split(" ")
        val first = args.first()
        val root = if (first.startsWith("/")) first.substring(1) else first
        subNodes.computeIfAbsent(root) { CommandNode() }.merge(command, args, 1)
    }

    private fun merge(command: ConfigurableCommand, args: List<String>, index: Int) {
        if (args.size == index) this.command = command
        else subNodes.computeIfAbsent(args[index]) { CommandNode() }.merge(command, args, index + 1)
    }
}