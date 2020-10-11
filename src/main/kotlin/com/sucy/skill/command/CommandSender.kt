package com.sucy.skill.command

interface CommandSender {
    val name: String
    fun sendMessage(message: String)
    fun hasPermission(permission: String): Boolean
}