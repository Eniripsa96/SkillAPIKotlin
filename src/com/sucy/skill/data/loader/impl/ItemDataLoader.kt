package com.sucy.skill.data.loader.impl

import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.internal.data.InternalItem
import com.sucy.skill.util.io.Data

object ItemDataLoader : DataLoader<Item> {
    private val DEFAULT_ITEM = InternalItem("PUMPKIN")

    override val transformers = emptyMap<Int, DataTransformer>()
    override val requiredKeys = arrayOf<String>()

    override fun load(data: Data): Item {
        val result = InternalItem(DEFAULT_ITEM.type)
        // TODO - implement
        return result
    }

    fun loadOrDefault(data: Data?): Item {
        return if (data == null) {
            DEFAULT_ITEM
        } else {
            load(data)
        }
    }
}