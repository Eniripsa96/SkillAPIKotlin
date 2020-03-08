package com.sucy.skill.facade.bukkit

import com.sucy.skill.SkillAPI
import com.sucy.skill.SkillAPIPlatform
import com.sucy.skill.facade.api.dependency.Dependencies
import com.sucy.skill.facade.bukkit.event.BukkitEventBusProxy
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

/**
 * SkillAPIKotlin © 2018
 */
class SkillAPIBukkit : JavaPlugin(), SkillAPIPlatform {
    override val eventBusProxy = BukkitEventBusProxy(this)
    override val scheduler = BukkitScheduler(this)
    override val server = BukkitServer(this)
    override val dependencies: Dependencies = Dependencies()

    override fun onEnable() {
        setUpDepenedencies()
        SkillAPI.init(this)
        SkillAPI.enable()
    }

    override fun onDisable() {
        SkillAPI.disable()
        HandlerList.unregisterAll(this)
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

    private fun setUpDepenedencies() {
        // TODO - initialize dependencies
    }
}