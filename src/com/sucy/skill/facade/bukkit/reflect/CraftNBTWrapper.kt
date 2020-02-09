package com.sucy.skill.facade.bukkit.reflect

import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Wrapper for NBT management
 */
class CraftNBTWrapper(private val item: ItemStack) {

    private lateinit var tags: MutableMap<String, Any>
    private lateinit var attributes: Any
    lateinit var meta: ItemMeta
        private set

    private var nextAttrId = 296218

    init {
        try {
            meta = metaField.get(item)?.let { it as ItemMeta } ?: item.itemMeta

            @Suppress("UNCHECKED_CAST")
            val currentTags = tagsField.get(meta)?.let { it as MutableMap<String, Any> }

            if (currentTags == null) {
                tags = mutableMapOf()
                tagsField.set(meta, tags)
            } else {
                tags = currentTags
            }

            attributes = tags["AttributeModifiers"] ?: tagList.newInstance()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setStat(key: String, value: Float) {
        try {
            val statTag = tagCompound.newInstance()
            setString.invoke(statTag, "AttributeName", key)
            setString.invoke(statTag, "AttributeName", key)
            setString.invoke(statTag, "Name", key)
            setFloat.invoke(statTag, "Amount", value)
            setInt.invoke(statTag, "Operation", 0)
            setInt.invoke(statTag, "UUIDLeast", nextAttrId)
            setInt.invoke(statTag, "UUIDMost", nextAttrId)
            add.invoke(attributes, statTag)

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)

            nextAttrId++
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    /**
     * @param key    tag key
     * @param string string value
     */
    operator fun set(key: String, string: String): CraftNBTWrapper {
        try {
            tags[key] = stringTag.newInstance(string)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return this
    }

    /**
     * @param key key to check for
     *
     * @return true if set, false otherwise
     */
    fun isSet(key: String): Boolean {
        return tags.containsKey(key)
    }

    /**
     * @param key key to use
     *
     * @return string value
     */
    fun getString(key: String): String {
        val raw = tags[key].toString()
        return if (raw.startsWith('"') && raw.endsWith('"')) {
            raw.substring(1, raw.length - 1)
        } else {
            raw
        }
    }

    /**
     * Finishes the modifications and returns the results
     *
     * @return resulting item
     */
    fun finish(): ItemStack {
        item.itemMeta = meta
        return item
    }

    companion object {

        private lateinit var metaField: Field
        private lateinit var tagsField: Field

        private lateinit var setString: Method
        private lateinit var setInt: Method
        private lateinit var setFloat: Method
        private lateinit var add: Method

        private lateinit var tagList: Class<*>
        private lateinit var tagCompound: Class<*>

        private lateinit var stringTag: Constructor<*>

        init {
            try {
                tagList = Reflection.getNMSClass("NBTTagList")
                tagCompound = Reflection.getNMSClass("NBTTagCompound")
                stringTag = Reflection.getNMSClass("NBTTagString").getConstructor(String::class.java)

                setString = tagCompound.getDeclaredMethod("setString", String::class.java, String::class.java)
                setInt = tagCompound.getDeclaredMethod("setInt", String::class.java, Int::class.javaPrimitiveType)
                setFloat = tagCompound.getDeclaredMethod("setFloat", String::class.java, Float::class.javaPrimitiveType)
                add = tagList.getDeclaredMethod("add", Reflection.getNMSClass("NBTBase"))

                val clazz = Reflection.getCraftClass("inventory.CraftMetaItem")

                metaField = ItemStack::class.java.getDeclaredField("meta")
                metaField.isAccessible = true
                tagsField = clazz.getDeclaredField("unhandledTags")
                tagsField.isAccessible = true
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}
