package com.sucy.skill.facade.bukkit

import com.sucy.skill.SkillAPI
import com.sucy.skill.SkillAPIPlatform
import com.sucy.skill.facade.bukkit.event.BukkitEventBusProxy
import org.bukkit.plugin.java.JavaPlugin

/**
 * SkillAPIKotlin © 2018
 */
class SkillAPIBukkit : JavaPlugin(), SkillAPIPlatform {
    override val eventBusProxy = BukkitEventBusProxy(this)

    override fun onEnable() {
        SkillAPI.init(this)
        SkillAPI.enable()
    }

    override fun onDisable() {
        SkillAPI.disable()
    }

    override fun reload() {
        SkillAPI.reload()
    }

    override fun getConfigFolder(): String {
        return dataFolder.absolutePath
    }

    override fun getResourceRoot(): String {
        return "./"
    }
}