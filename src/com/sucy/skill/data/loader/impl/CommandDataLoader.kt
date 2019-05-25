package com.sucy.skill.data.loader.impl

import com.sucy.skill.command.ConfigurableCommand
import com.sucy.skill.data.loader.ContextualDataLoader
import com.sucy.skill.data.loader.transform.legacy.LegacyCommandTransformer
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.text.color
import com.sucy.skill.util.text.uncolor

object CommandDataLoader : ContextualDataLoader<ConfigurableCommand> {
    const val PATH = "path"
    const val DESCRIPTION = "description"
    const val ARGUMENTS = "arguments"
    const val PERMISSION = "permission"
    const val ENABLED = "enabled"
    const val COOLDOWN = "cooldown"
    const val LANGUAGE = "language"

    override val transformers = mapOf(1 to LegacyCommandTransformer)

    override fun load(key: String, data: Data, context: ConfigurableCommand): ConfigurableCommand {
        val permission = data.getString(PERMISSION, context.permission ?: "")

        val language = data.getOrCreateSection(LANGUAGE)
        context.logic.defaultMessages.forEach { context.logic.messages[it.key] = it.value.color() }
        language.forEach {
            context.logic.messages.computeIfPresent(it) { key, value -> language.getStringList(key, value).color() }
        }

        return context.copy(
                path = data.getString(PATH, context.path),
                description = data.getString(DESCRIPTION, context.description),
                arguments = data.getString(ARGUMENTS, context.arguments),
                enabled = data.getBoolean(ENABLED, context.enabled),
                cooldownSeconds = data.getDouble(COOLDOWN, context.cooldownSeconds),
                permission = if (permission.isBlank()) null else permission.trim()
        )
    }

    override fun serialize(data: ConfigurableCommand): Data {
        val result = Data()
        result.set(PATH, data.path)
        result.set(DESCRIPTION, data.description)
        result.set(ARGUMENTS, data.arguments)
        result.set(PERMISSION, data.permission ?: "")

        val language = result.createSection(LANGUAGE)
        data.logic.messages.forEach { key, value -> language.set(key, value.uncolor()) }

        return result
    }
}