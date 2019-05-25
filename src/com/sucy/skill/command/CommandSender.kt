package com.sucy.skill.command

interface CommandSender {
    fun sendMessage(message: String)
    fun hasPermission(permission: String): Boolean
}