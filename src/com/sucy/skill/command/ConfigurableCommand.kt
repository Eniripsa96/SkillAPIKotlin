package com.sucy.skill.command

import com.sucy.skill.facade.api.entity.Player

data class ConfigurableCommand(
        val key: String,
        var path: String,
        var logic: CommandLogic,
        var playerOnly: Boolean = false,
        var description: String = "",
        var arguments: String = "",
        var permission: String? = null,
        var condition: (Player) -> Boolean
)
