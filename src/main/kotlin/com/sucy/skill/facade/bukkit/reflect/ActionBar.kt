package com.sucy.skill.facade.bukkit.reflect

import com.sucy.skill.facade.bukkit.util.BukkitVersion
import com.sucy.skill.facade.bukkit.util.BukkitVersion.v1_12
import com.sucy.skill.util.log.Logger
import org.bukkit.entity.Player
import java.lang.reflect.Constructor
import java.lang.reflect.Method

object ActionBar {
    private var packet: Class<*>? = null
    private var getHandle: Method? = null
    private var constructPacket: Constructor<*>? = null
    private var constructText: Constructor<*>? = null
    private var messageType: Any = 2.toByte()

    var isSupported: Boolean = false
        private set

    init {
        try {
            val craftPlayer = Reflection.getCraftClass("entity.CraftPlayer")
            val chatPacket = Reflection.getNMSClass("PacketPlayOutChat")
            val chatBase = Reflection.getNMSClass("IChatBaseComponent")
            val chatText = Reflection.getNMSClass("ChatComponentText")
            packet = Reflection.getNMSClass("Packet")

            if (BukkitVersion.isAtLeast(v1_12)) {
                val chatMessageType = Reflection.getNMSClass("ChatMessageType")
                messageType = chatMessageType.getMethod("a", Byte::class.java).invoke(null, messageType)
                constructPacket = chatPacket.getConstructor(chatBase, chatMessageType)
            } else {
                constructPacket = chatPacket.getConstructor(chatBase, Byte::class.java)
            }
            constructText = chatText.getConstructor(String::class.java)
            getHandle = craftPlayer.getDeclaredMethod("getHandle")

            isSupported = true
        } catch (ex: Exception) {
            Logger.warn("Failed to set up Action Bar - your server type/version may not be supported")
            ex.printStackTrace()
        }
    }

    fun show(player: Player, message: String) {
        if (!isSupported) return

        try {
            val text = constructText!!.newInstance(message)
            val data = constructPacket!!.newInstance(text, messageType)
            val handle = getHandle!!.invoke(player)
            val connection = Reflection.getValue(handle, "playerConnection")
            val send = Reflection.getMethod(connection, "sendPacket")
            send.invoke(connection, data)
        } catch (ex: Exception) {
            Logger.error("Failed to update action bar for ${player.name} - disabling to prevent further errors")
            ex.printStackTrace()
            isSupported = false
        }
    }
}