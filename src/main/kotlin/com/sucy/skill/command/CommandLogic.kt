package com.sucy.skill.command

import com.sucy.skill.util.text.Filter

abstract class CommandLogic {
    abstract val defaultMessages: Map<String, List<String>>
    val messages = mutableMapOf<String, List<String>>()

    abstract fun execute(sender: CommandSender, args: CommandArguments)

    fun sendMessage(recipient: CommandSender, key: String) {
        messages[key]?.forEach(recipient::sendMessage)
    }

    fun sendMessage(recipient: CommandSender, key: String, filters: Map<String, String>) {
        messages[key]?.forEach {
            recipient.sendMessage(Filter.apply(it, filters))
        }
    }
}
