package com.sucy.skill.command

/**
 * Handles building out relations between commands and handles delegating between them
 */
class CommandNode {
    private val subNodes = HashMap<String, CommandNode>()

    var command: ConfigurableCommand? = null
        private set

    val keys: Set<String>
        get() = subNodes.keys

    fun execute(sender: CommandSender, args: List<String>) {
        val first = args.first()
        val root = if (first.startsWith("/")) first.substring(1) else first
        subNodes[root]?.execute(sender, args,1)
    }

    fun execute(sender: CommandSender, args: List<String>, index: Int) {
        val node = subNodes[args[index]]
        if (node == null) {
            command?.logic?.execute(sender, CommandArguments(args.subList(index, args.size)))
        } else {
            node.execute(sender, args, index + 1)
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