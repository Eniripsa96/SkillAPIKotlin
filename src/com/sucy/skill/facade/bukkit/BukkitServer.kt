package com.sucy.skill.facade.bukkit

import com.sucy.skill.facade.api.Server
import com.sucy.skill.facade.api.World
import com.sucy.skill.facade.api.managers.TaskManager
import com.sucy.skill.facade.bukkit.managers.BukkitPlayerManager
import com.sucy.skill.facade.bukkit.managers.BukkitTaskManager
import org.bukkit.Bukkit

class BukkitServer(plugin: SkillAPIBukkit) : Server {
    override val players = BukkitPlayerManager()
    override val taskManager = BukkitTaskManager(plugin)
    override fun getWorld(name: String) = BukkitWorld(Bukkit.getWorld(name))
}
