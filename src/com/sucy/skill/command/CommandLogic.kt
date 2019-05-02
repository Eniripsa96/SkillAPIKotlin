package com.sucy.skill.command

import com.sucy.skill.util.text.Filter

interface CommandLogic {
    var messages: Map<String, List<String>>

    fun execute(sender: CommandSender, args: CommandArguments)

    fun sendMessage(recipient: CommandSender, key: String) {
        messages[key]?.forEach(recipient::sendMessage)
    }

    fun sendMessage(recipient: CommandSender, key: String, filters: Map<String, String>) {
        messages[key]?.forEach {
            recipient.sendMessage(Filter.apply(it, filters))
        }
    }
}
