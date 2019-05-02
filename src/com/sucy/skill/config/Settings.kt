package com.sucy.skill.config

import com.sucy.skill.config.category.*
import com.sucy.skill.util.io.Config
import com.sucy.skill.util.io.ConfigHolder
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class Settings(private val plugin: ConfigHolder) {
    private val mainConfig = loadConfig("config")
    private val groupSettings = HashMap<String, GroupSettings>()

    val account = AccountSettings(mainConfig.getOrCreateSection("Accounts"))
    val classes = ClassSettings(mainConfig.getOrCreateSection("Classes"))
    val expYields = ExpYieldsConfig(loadConfig("exp"))
    val mana = ManaSettings(mainConfig.getOrCreateSection("Mana"))
    val saving = SavingSettings(mainConfig.getOrCreateSection("Saving"))
    val skills = SkillSettings(mainConfig.getOrCreateSection("Skills"))
    val targeting = TargetingConfig(mainConfig.getOrCreateSection("Targeting"))
    val worlds = WorldSettings(mainConfig.getOrCreateSection("Worlds"))

    fun forGroup(group: String): GroupSettings {
        return groupSettings.computeIfAbsent(group) {
            GroupSettings(loadConfig("group/$group", resource = "group"))
        }
    }

    private fun loadConfig(name: String, resource: String = name): Data {
        val config = Config(plugin, name)
        config.checkDefaults()
        config.save()
        return config.data
    }
}