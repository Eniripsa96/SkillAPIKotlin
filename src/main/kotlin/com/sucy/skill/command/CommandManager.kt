package com.sucy.skill.command

import com.sucy.skill.SkillAPI
import com.sucy.skill.command.admin.CmdAp
import com.sucy.skill.command.admin.CmdExp
import com.sucy.skill.command.basic.CmdCast
import com.sucy.skill.command.basic.CmdProfess
import com.sucy.skill.data.loader.impl.CommandDataLoader
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.util.io.Config

object CommandManager {
    fun loadCommands(): CommandNode {
        val config = Config(SkillAPI.plugin, "commands")
        val version = config.data.getVersion()

        val commands = COMMANDS.map {
            CommandDataLoader.transformAndLoad(
                key = it.key,
                data = config.data.getOrCreateSection(it.key),
                version = version,
                context = it
            )
        }

        val root = CommandNode()
        commands.forEach(root::merge)

        return root
    }

    private val ANYONE: (CommandSender) -> Boolean = { true }
    private val PLAYER_ONLY: (CommandSender) -> Boolean = { it is Player }
    private val ACTOR_ONLY: (CommandSender) -> Boolean = { it is Actor }

    private val COMMANDS = listOf(
        // Admin
        ConfigurableCommand(
            key = "ap",
            path = "/class ap",
            logic = CmdAp,
            description = "Gives a player AP",
            arguments = "[player] <amount> [message]",
            permission = "skillapi.admin",
            condition = ANYONE
        ),
        ConfigurableCommand(
            key = "exp",
            path = "/class exp",
            logic = CmdExp,
            description = "Gives a player Experience",
            arguments = "[player] <amount> [group] [message]",
            permission = "skillapi.admin",
            condition = ANYONE
        ),

        // Basic
        ConfigurableCommand(
            key = "cast",
            path = "/class cast",
            logic = CmdCast,
            description = "Casts a skill",
            arguments = "<skill>",
            permission = "skillapi.basic",
            condition = ACTOR_ONLY
        ),
        ConfigurableCommand(
            key = "profess",
            path = "/class profess",
            logic = CmdProfess,
            description = "Chooses a class",
            arguments = "<class>",
            permission = "skillapi.basic",
            enabled = true,
            condition = PLAYER_ONLY
        )
    )
}