package com.sucy.skill.facade.bukkit.util

import com.sucy.skill.util.log.Logger
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.plugin.Plugin

object BukkitVersion {
    const val v1_8 = 1_08_00
    const val v1_9 = 1_09_00
    const val v1_10 = 1_10_00
    const val v1_11 = 1_11_00
    const val v1_12 = 1_12_00
    const val v1_13 = 1_13_00
    const val v1_14 = 1_14_00

    private var serverType = ServerType.SPIGOT
    private var version = -1

    fun isAtLeast(target: Int): Boolean {
        return target >= version
    }

    fun getMajorVersion(): Int = version / 100

    private fun initialize(vs: String) {
        try {
            val i = vs.indexOf("MC:") + 4
            val j = vs.indexOf(")", i)
            if (i < 4 || j < 0) return

            val v = vs.substring(i, j)
            val pieces = v.split(".")
            version = pieces[0].toInt() * 10_000 + pieces[1].toInt() * 100

            if (pieces.size > 2) {
                version += pieces[2].toInt()
            }
        } catch (ex: Exception) {
            serverType = ServerType.UNKNOWN
            version = 999999
        }

        Logger.info("Server version: $version")
    }

    init {
        val sender = object : CommandSender {
            override fun sendMessage(p0: String) {
                initialize(p0)
            }

            override fun sendMessage(p0: Array<out String>) {
                p0.forEach { initialize(it) }
            }

            override fun isPermissionSet(p0: String?): Boolean = true

            override fun isPermissionSet(p0: Permission?): Boolean = true

            override fun addAttachment(p0: Plugin?, p1: String?, p2: Boolean): PermissionAttachment {
                TODO("not implemented")
            }

            override fun addAttachment(p0: Plugin?): PermissionAttachment {
                TODO("not implemented")
            }

            override fun addAttachment(p0: Plugin?, p1: String?, p2: Boolean, p3: Int): PermissionAttachment {
                TODO("not implemented")
            }

            override fun addAttachment(p0: Plugin?, p1: Int): PermissionAttachment {
                TODO("not implemented")
            }

            override fun getName(): String = "Version Checker"

            override fun isOp(): Boolean = true

            override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> = mutableSetOf()

            override fun getServer(): Server = Bukkit.getServer()

            override fun removeAttachment(p0: PermissionAttachment?) {}

            override fun recalculatePermissions() {}

            override fun hasPermission(p0: String?): Boolean = true

            override fun hasPermission(p0: Permission?): Boolean = true

            override fun setOp(p0: Boolean) {}
        }

        Bukkit.getServer().dispatchCommand(sender, "version")
    }
}