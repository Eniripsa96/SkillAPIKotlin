package com.sucy.skill.command.basic

import com.sucy.skill.SkillAPI
import com.sucy.skill.command.*
import com.sucy.skill.facade.api.entity.Player
import kotlin.math.sin

object CmdProfess : CommandLogic() {

    // Messages
    private const val CONSOLE = "console"
    private const val INVALID_CLASS = "invalid-class"
    private const val PROFESSED = "professed"
    private const val CANNOT_PROFESS = "cannot-profess"
    private const val DISABLED = "world-disabled"
    private const val NOT_AVAILABLE = "not-available"
    private const val MISSING_OPTION = "missing-option"

    // Arguments
    private const val CLASS_NAME = "className"

    override val argumentPattern = ArgumentPattern(listOf(ArgumentDefinition(CLASS_NAME, single = false)))

    override var defaultMessages = mapOf(
        CONSOLE to listOf("&2The console cannot profess as a class"),
        INVALID_CLASS to listOf("&6{class} &2is not a valid class"),
        MISSING_OPTION to listOf("&2You must provide a class. Use $6/class options &2 to view available classes"),
        PROFESSED to listOf("&2You are now a &6{class}"),
        CANNOT_PROFESS to listOf("&2You cannot become a &6{class}"),
        DISABLED to listOf("&4Classes are disabled in this world"),
        NOT_AVAILABLE to listOf("&2No further professions are available at this time")
    )

    override fun execute(sender: CommandSender, args: Map<String, String>) {
        if (sender !is Player) return sendMessage(sender, CONSOLE)

        val name = args.getValue(CLASS_NAME)
        val profession = SkillAPI.registry.getProfession(name)
        val filters = mapOf("class" to name)

        when {
            profession == null -> sendMessage(sender, INVALID_CLASS, filters)
            sender.activeAccount.professionSet.canProfess(profession) ->
                sender.activeAccount.professionSet.profess(sender, profession)
            else -> sendMessage(sender, CANNOT_PROFESS, filters)
        }
    }
}