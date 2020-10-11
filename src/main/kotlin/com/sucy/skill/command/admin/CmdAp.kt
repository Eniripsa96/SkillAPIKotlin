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

object CmdAp : CommandLogic() {

    // Messages
    private const val NOT_PLAYER = "not-player"
    private const val NOT_POSITIVE = "not-positive"
    private const val GAVE_AP = "gave-ap"
    private const val RECEIVED_AP = "received-ap"

    // Arguments
    private const val PLAYER = "player"
    private const val AMOUNT = "amount"
    private const val MESSAGE = "message"

    override val argumentPattern = ArgumentPattern(listOf(
        ArgumentDefinition(PLAYER, optional = true, single = false) { !it.isInteger() },
        ArgumentDefinition(AMOUNT) { it.isInteger() },
        ArgumentDefinition(MESSAGE, optional = true) { it.isBoolean() }
    ))

    override var defaultMessages = mapOf(
            NOT_PLAYER to listOf("&4{player} is not a valid player name"),
            NOT_POSITIVE to listOf("&4You must give a positive amount of AP"),
            GAVE_AP to listOf("&2You have given &6{player} {ap} AP"),
            RECEIVED_AP to listOf("&2You have received &6{ap} AP &2from &6{player}")
    )

    override fun execute(sender: CommandSender, args: Map<String, String>) {
        val target = args[PLAYER]?.let {
            SkillAPI.server.players.getPlayer(it) ?: return sendMessage(sender, NOT_PLAYER, mapOf("player" to it))
        } ?: sender

        if (target !is Player) return // TODO - display usage

        val amountString = args.getValue(AMOUNT)

        val amount = amountString.toInt()
        if (amount <= 0) return sendMessage(sender, NOT_POSITIVE, mapOf("amount" to amountString))

        target.activeAccount.attributePoints += amount

        val showMessage = args[MESSAGE]?.toBoolean() ?: true
        if (showMessage) {
            if (target != sender) sendMessage(sender, GAVE_AP, mapOf(PLAYER to target.name, "ap" to amountString))
            if (target.isOnline) sendMessage(target, RECEIVED_AP, mapOf(PLAYER to sender.name, "ap" to amountString))
        }
    }
}