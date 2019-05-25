package com.sucy.skill.data.loader.transform.legacy

import com.sucy.skill.data.loader.impl.CommandDataLoader.ARGUMENTS
import com.sucy.skill.data.loader.impl.CommandDataLoader.COOLDOWN
import com.sucy.skill.data.loader.impl.CommandDataLoader.DESCRIPTION
import com.sucy.skill.data.loader.impl.CommandDataLoader.ENABLED
import com.sucy.skill.data.loader.impl.CommandDataLoader.LANGUAGE
import com.sucy.skill.data.loader.impl.CommandDataLoader.PATH
import com.sucy.skill.data.loader.impl.CommandDataLoader.PERMISSION
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

object LegacyCommandTransformer : DataTransformer {
    override fun transform(key: String, data: Data): Data {
        val result = Data()

        data.getString("name")?.let { result.set(PATH, "/class $it") }
        data.getString("description")?.let { result.set(DESCRIPTION, it) }
        data.getString("permission")?.let { result.set(PERMISSION, it) }
        data.getString("args")?.let { result.set(ARGUMENTS, it) }
        result.set(ENABLED, data.getBoolean(ENABLED, true))
        result.set(COOLDOWN, data.getInt("cooldown", 0))

        val messages = data.getOrCreateSection("messages")
        val language = result.createSection(LANGUAGE)
        messages.forEach {
            language.set(it, messages.getStringList(it))
        }

        return result
    }
}