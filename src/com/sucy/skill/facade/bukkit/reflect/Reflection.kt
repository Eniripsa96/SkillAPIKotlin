package com.sucy.skill.facade.bukkit.reflect

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Utility class for performing reflection operations. Only use
 * this class if you know what you're doing.
 */
object Reflection {
    private lateinit var craft: String
    private lateinit var nms: String
    private lateinit var packetClass: Class<*>

    private lateinit var getHandle: Method
    private lateinit var sendPacket: Method

    private lateinit var connection: Field

    init {
        try {
            nms = "net.minecraft.server." + Bukkit.getServer().javaClass.getPackage().name.substring(23) + '.'.toString()
            craft = "org.bukkit.craftbukkit." + Bukkit.getServer().javaClass.getPackage().name.substring(23) + '.'.toString()

            packetClass = Class.forName(nms + "Packet")
            getHandle = Class.forName(craft + "entity.CraftPlayer").getDeclaredMethod("getHandle")
            connection = Class.forName(nms + "EntityPlayer").getDeclaredField("playerConnection")
            sendPacket = Class.forName(nms + "PlayerConnection").getDeclaredMethod("sendPacket", packetClass)
        } catch (ex: Exception) {
            println("Failed to set up reflection - is the server using Cauldron/Thermos?")
        }
    }

    /**
     * Retrieves a class by name
     *
     * @param name name of the class including packages
     *
     * @return class object or null if invalid
     */
    fun getClass(name: String): Class<*> {
        return Class.forName(name)
    }

    /**
     * Retrieves an NMS class by name
     *
     * @param name name of the class including packages
     *
     * @return class object or null if invalid
     */
    fun getNMSClass(name: String): Class<*> {
        return getClass(nms + name)
    }

    /**
     * Retrieves a CraftBukkit class by name
     *
     * @param name name of the class including packages
     *
     * @return class object or null if invalid
     */
    fun getCraftClass(name: String): Class<*> {
        return getClass(craft + name)
    }

    /**
     * Gets an instance of the class
     *
     * @param c    class to get an instance of
     * @param args constructor arguments
     *
     * @return instance of the class or null if unable to create the object
     */
    fun getInstance(c: Class<*>, vararg args: Any): Any {
        for (constructor in c.declaredConstructors)
            if (constructor.genericParameterTypes.size == args.size)
                return constructor.newInstance(*args)
        throw IllegalArgumentException("No constructor for $c matches args $args")
    }

    /**
     * Tries to set a value for the object
     *
     * @param o         object reference
     * @param fieldName name of the field to set
     * @param value     value to set
     */
    fun setValue(o: Any, fieldName: String, value: Any) {
        val field = o.javaClass.getDeclaredField(fieldName)
        if (!field.isAccessible) field.isAccessible = true
        field.set(o, value)
    }

    /**
     * Tries to get a value from the object
     *
     * @param o         object reference
     * @param fieldName name of the field to retrieve the value from
     *
     * @return the value of the field or null if not found
     */
    fun getValue(o: Any, fieldName: String): Any {
        val field = o.javaClass.getDeclaredField(fieldName)
        if (!field.isAccessible) field.isAccessible = true
        return field.get(o)
    }

    /**
     * Tries to get a method from the object
     *
     * @param o          object reference
     * @param methodName name of the field to retrieve the value from
     *
     * @return the value of the field or null if not found
     */
    fun getMethod(o: Any, methodName: String, vararg params: Class<*>): Method {
        val method = o.javaClass.getMethod(methodName, *params)
        if (!method.isAccessible) method.isAccessible = true
        return method
    }

    /**
     * Tries to send a packet to the player
     *
     * @param player player to send to
     * @param packet packet to send
     */
    fun sendPacket(player: Player, packet: Any) {
        val handle = getHandle.invoke(player)
        val con = connection.get(handle)
        sendPacket.invoke(con, packet)
    }

    /**
     * Tries to send a packet to the player
     *
     * @param player  player to send to
     * @param packets list of packets to send
     */
    fun sendPackets(player: Player, packets: List<Any>) {
        val handle = getHandle.invoke(player)
        val con = connection.get(handle)
        packets.forEach { sendPacket.invoke(con, it) }
    }
}
