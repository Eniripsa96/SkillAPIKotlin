package com.sucy.skill.facade.bukkit

import com.sucy.skill.SkillAPIPlugin
import com.sucy.skill.facade.bukkit.event.BukkitEventBusProxy
import org.bukkit.plugin.java.JavaPlugin

/**
 * SkillAPIKotlin © 2018
 */
class SkillAPIBukkit : JavaPlugin(), SkillAPIPlugin {
    override val eventBusProxy = BukkitEventBusProxy(this)

    override fun reload() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getConfigFolder(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getResourceRoot(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}