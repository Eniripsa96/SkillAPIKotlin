package com.sucy.skill.command.basic

import com.sucy.skill.SkillAPI
import com.sucy.skill.command.CommandArguments
import com.sucy.skill.command.CommandLogic
import com.sucy.skill.command.CommandSender
import com.sucy.skill.facade.api.entity.Player

object CmdProfess : CommandLogic {
    const val CONSOLE = "console"
    const val INVALID_CLASS = "invalid-class"
    const val PROFESSED = "professed"
    const val CANNOT_PROFESS = "cannot-profess"
    const val DISABLED = "world-disabled"
    const val NOT_AVAILABLE = "not-available"

    override var messages = mapOf(
            Pair(CONSOLE, listOf("&2The console cannot profess as a class")),
            Pair(INVALID_CLASS, listOf("&6{class} &2is not a valid class")),
            Pair(PROFESSED, listOf("&2You are now a &6{class}")),
            Pair(CANNOT_PROFESS, listOf("&2You cannot become a &6{class}")),
            Pair(DISABLED, listOf("&4Classes are disabled in this world")),
            Pair(NOT_AVAILABLE, listOf("&2No further professions are available at this time"))
    )

    override fun execute(sender: CommandSender, args: CommandArguments) {
        when {
            sender !is Player -> sendMessage(sender, CONSOLE)
            SkillAPI.settings.worlds.isWorldEnabled(sender.world.name) -> sendMessage(sender, DISABLED)
            args.input.isEmpty() -> {
                // TODO - show options
            }
            else -> {
                val name = args.input.joinToString(separator = " ")
                val profession = SkillAPI.registry.getProfession(name)
                val filters = mapOf(Pair("class", name))

                when {
                    profession == null -> sendMessage(sender, INVALID_CLASS, filters)
                    sender.activeAccount.professionSet.canProfess(profession) -> {
                        sender.activeAccount.professionSet.profess(sender, profession)
                    }
                    else -> sendMessage(sender, CANNOT_PROFESS, filters)
                }
            }
        }
    }
}