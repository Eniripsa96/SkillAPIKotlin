package com.sucy.skill.config

import com.sucy.skill.config.category.*
import com.sucy.skill.util.io.Config
import com.sucy.skill.util.io.ConfigHolder
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class Settings(private val plugin: ConfigHolder) {
    val account = AccountSettings(loadConfig("config").getOrCreateSection("Accounts"))
    val classes = ClassSettings(loadConfig("config").getOrCreateSection("Classes"))
    val expYields = ExpYieldsConfig(loadConfig("exp"))
    val mana = ManaSettings(loadConfig("config").getOrCreateSection("Mana"))
    val saving = SavingSettings(loadConfig("config").getOrCreateSection("Saving"))
    val skills = SkillSettings(loadConfig("config").getOrCreateSection("Skills"))
    val targeting = TargetingConfig(loadConfig("config").getOrCreateSection("Targeting"))

    private fun loadConfig(name: String): Data {
        val config = Config(plugin, name)
        config.checkDefaults()
        config.save()
        return config.data
    }
}