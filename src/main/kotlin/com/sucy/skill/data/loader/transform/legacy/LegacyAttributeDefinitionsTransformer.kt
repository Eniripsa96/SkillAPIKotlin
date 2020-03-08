package com.sucy.skill.data.loader.transform.legacy

import com.sucy.skill.data.loader.impl.attribute.AttributeDataLoader.DISPLAY_NAME
import com.sucy.skill.data.loader.impl.attribute.AttributeDataLoader.EFFECTS
import com.sucy.skill.data.loader.impl.attribute.AttributeDataLoader.ICON
import com.sucy.skill.data.loader.impl.attribute.AttributeDataLoader.MAX
import com.sucy.skill.data.loader.impl.attribute.AttributeDataLoader.STATS
import com.sucy.skill.data.loader.impl.common.ItemDataLoader.DATA
import com.sucy.skill.data.loader.impl.common.ItemDataLoader.LORE
import com.sucy.skill.data.loader.impl.common.ItemDataLoader.TYPE
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

object LegacyAttributeDefinitionsTransformer : DataTransformer {
    override fun transform(key: String, data: Data): Data {
        val result = Data()
        result.set(DISPLAY_NAME, data.getString("display", key))
        result.set(MAX, data.getInt("max"))
        data.getSection("stats")?.let { result.set(STATS, it) }

        val iconData = result.createSection(ICON)
        iconData.set(TYPE, data.getString("icon", "pumpkin"))
        iconData.set(DATA, data.getInt("icon-data", 0))
        iconData.set(LORE, data.getStringList("icon-lore"))

        data.getSection("global")?.let { global ->
            val effectData = result.createSection(EFFECTS)

            global.forEach { type ->
                val typeData = effectData.createSection(type)
                val oldData = global.getOrCreateSection(type)

                oldData.forEach { key ->
                    oldData.getString(key)?.let { typeData.set(key, it.split('|')) }
                }
            }
        }

        return result
    }
}