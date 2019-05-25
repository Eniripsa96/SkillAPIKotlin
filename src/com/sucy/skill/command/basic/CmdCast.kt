package com.sucy.skill.command.basic

import com.sucy.skill.SkillAPI
import com.sucy.skill.command.CommandArguments
import com.sucy.skill.command.CommandLogic
import com.sucy.skill.command.CommandSender
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player

object CmdCast : CommandLogic() {
    private const val CONSOLE = "console"
    private const val INVALID_SKILL = "invalid-skill"
    private const val PROFESSED = "professed"
    private const val CANNOT_PROFESS = "cannot-profess"
    private const val DISABLED = "world-disabled"
    private const val NOT_AVAILABLE = "not-available"

    override var defaultMessages = mapOf(
            CONSOLE to listOf("&2The console cannot cast a skill"),
            INVALID_SKILL to listOf("&6{skill} &2is not a valid skill"),
            PROFESSED to listOf("&2You are now a &6{class}"),
            CANNOT_PROFESS to listOf("&2You cannot become a &6{class}"),
            DISABLED to listOf("&4Skills are disabled in this world"),
            NOT_AVAILABLE to listOf("&2You have not unlocked that skill yet")
    )

    override fun execute(sender: CommandSender, args: CommandArguments) {
        when {
            sender !is Actor -> sendMessage(sender, CONSOLE)
            SkillAPI.settings.worlds.isWorldEnabled(sender.world.name) -> sendMessage(sender, DISABLED)
            args.input.isEmpty() -> {
                // TODO - show options
            }
            else -> {
                val name = args.input.joinToString(separator = " ")
                val skill = sender.skills[name]
                val filters = mapOf("skill" to name)

                when {
                    skill == null -> sendMessage(sender, INVALID_SKILL, filters)
                    !skill.isUnlocked -> sendMessage(sender, NOT_AVAILABLE)
                    else -> sender.cast(skill)
                }
            }
        }
    }
}