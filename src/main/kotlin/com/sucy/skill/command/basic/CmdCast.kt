package com.sucy.skill.command.basic

import com.sucy.skill.SkillAPI
import com.sucy.skill.command.*
import com.sucy.skill.facade.api.entity.Actor
import kotlin.math.sin

object CmdCast : CommandLogic() {

    // Messages
    private const val CONSOLE = "console"
    private const val INVALID_SKILL = "invalid-skill"
    private const val PROFESSED = "professed"
    private const val CANNOT_PROFESS = "cannot-profess"
    private const val DISABLED = "world-disabled"
    private const val NOT_AVAILABLE = "not-available"

    // Arguments
    private const val SKILL_NAME = "skillName"

    override val argumentPattern = ArgumentPattern(listOf(ArgumentDefinition(SKILL_NAME, single = false)))

    override val defaultMessages = mapOf(
            CONSOLE to listOf("&2The console cannot cast a skill"),
            INVALID_SKILL to listOf("&6{skill} &2is not a valid skill"),
            PROFESSED to listOf("&2You are now a &6{class}"),
            CANNOT_PROFESS to listOf("&2You cannot become a &6{class}"),
            DISABLED to listOf("&4Skills are disabled in this world"),
            NOT_AVAILABLE to listOf("&2You have not unlocked that skill yet")
    )

    override fun execute(sender: CommandSender, args: Map<String, String>) {
        if (sender !is Actor) return sendMessage(sender, CONSOLE)

        val name = args.getValue(SKILL_NAME)
        val skill = sender.skills[name]
        val filters = mapOf("skill" to name)

        when {
            skill == null -> sendMessage(sender, INVALID_SKILL, filters)
            !skill.isUnlocked -> sendMessage(sender, NOT_AVAILABLE)
            else -> sender.cast(skill)
        }
    }
}