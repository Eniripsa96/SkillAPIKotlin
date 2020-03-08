package com.sucy.skill.command

data class ConfigurableCommand(
        val key: String,
        var path: String,
        var logic: CommandLogic,
        var description: String = "",
        var arguments: String = "",
        var permission: String? = null,
        val enabled: Boolean = true,
        val cooldownSeconds: Double = 0.0,
        var condition: (CommandSender) -> Boolean
)
