package com.sucy.skill.command

interface CommandLogic {
    fun execute(sender: CommandSender, args: CommandArguments)
}
