package com.sucy.skill.data.loader.impl.common

import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.internal.data.InternalItem
import com.sucy.skill.util.io.Data

object ItemDataLoader : DataLoader<Item> {
    const val TYPE = "type"
    const val DURABILITY = "durability"
    const val DATA = "data"
    private const val AMOUNT = "amount"
    private const val NAME = "name"
    const val LORE = "lore"
    private const val VISIBILITY = "visibility"
    private const val TAGS = "tags"

    private val DEFAULT_ITEM = InternalItem("PUMPKIN")

    override val transformers = emptyMap<Int, DataTransformer>()
    override val requiredKeys = arrayOf<String>()

    override fun load(key: String, data: Data): Item {
        return InternalItem(
                type = data.getString(TYPE, DEFAULT_ITEM.type),
                durability = data.getInt(DURABILITY, 0).toShort(),
                data = data.getInt(DATA, 0).toByte(),
                amount = data.getInt(AMOUNT, 1),
                name = data.getString(NAME),
                lore = data.getStringList(LORE),
                visibility = data.getInt(VISIBILITY),
                tags = data.getOrCreateSection(TAGS).asMap()
        )
    }

    fun loadOrDefault(data: Data?): Item {
        return if (data == null) {
            DEFAULT_ITEM
        } else {
            load("", data)
        }
    }

    override fun serialize(data: Item): Data {
        val result = Data()

        result.set(TYPE, data.type)
        result.set(DURABILITY, data.durability)
        result.set(DATA, data.data)
        result.set(AMOUNT, data.amount)
        data.name?.let { result.set(NAME, it) }
        result.set(LORE, data.lore)
        result.set(VISIBILITY, data.visibility)
        result.set(TAGS, data.tags)

        return result
    }
}