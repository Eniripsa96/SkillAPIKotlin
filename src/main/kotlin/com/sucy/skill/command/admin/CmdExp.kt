package com.sucy.skill.command.admin

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.profession.ExpSource
import com.sucy.skill.command.ArgumentDefinition
import com.sucy.skill.command.ArgumentPattern
import com.sucy.skill.command.CommandLogic
import com.sucy.skill.command.CommandSender
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.util.text.isBoolean
import com.sucy.skill.util.text.isInteger

object CmdExp : CommandLogic() {

    // Messages
    private const val NOT_PLAYER = "not-player"
    private const val NOT_POSITIVE = "not-positive"
    private const val GAVE_EXP = "gave-exp"
    private const val RECEIVED_EXP = "received-exp"

    // Arguments
    private const val PLAYER = "player"
    private const val AMOUNT = "amount"
    private const val GROUP = "group"
    private const val MESSAGE = "message"

    override val argumentPattern = ArgumentPattern(listOf(
        ArgumentDefinition(PLAYER, optional = true, single = false) { !it.isInteger() },
        ArgumentDefinition(AMOUNT) { it.isInteger() },
        ArgumentDefinition(GROUP, optional = true) { !it.isBoolean() },
        ArgumentDefinition(MESSAGE, optional = true) { it.isBoolean() }
    ))

    override var defaultMessages = mapOf(
            NOT_PLAYER to listOf("&4{player} is not a valid player name"),
            NOT_POSITIVE to listOf("&4You must give a positive amount of experience"),
            GAVE_EXP to listOf("&2You have given &6{player} {exp} experience"),
            RECEIVED_EXP to listOf("&2You have received &6{exp} experience &2from &6{player}")
    )

    override fun execute(sender: CommandSender, args: Map<String, String>) {
        val target = args[PLAYER]?.let {
            SkillAPI.server.players.getPlayer(it) ?: return sendMessage(sender, NOT_PLAYER, mapOf("player" to it))
        } ?: sender

        if (target !is Player) return // TODO - display usage

        val amountString = args.getValue(AMOUNT)

        val amount = amountString.toInt()
        if (amount <= 0) return sendMessage(sender, NOT_POSITIVE, mapOf("amount" to amountString))

        val group = args[GROUP]
        if (group == null) target.activeAccount.professionSet.giveExp(target, amount.toDouble(), ExpSource.COMMAND.name)
        else target.activeAccount.professionSet.giveExp(target, amount.toDouble(), group)

        val showMessage = args[MESSAGE]?.toBoolean() ?: true
        if (showMessage) {
            if (target != sender) sendMessage(sender, GAVE_EXP, mapOf(PLAYER to target.name, "exp" to amountString))
            if (target.isOnline) sendMessage(target, RECEIVED_EXP, mapOf(PLAYER to sender.name, "exp" to amountString))
        }
    }
}