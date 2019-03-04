package com.sucy.skill.facade.bukkit.managers

import com.sucy.skill.facade.api.managers.Task
import com.sucy.skill.facade.api.managers.TaskManager
import com.sucy.skill.facade.bukkit.SkillAPIBukkit
import org.bukkit.Bukkit

class BukkitTaskManager(val plugin: SkillAPIBukkit) : TaskManager {
    override fun run(task: Runnable, delay: Long): Task {
        val scheduled = Bukkit.getServer().scheduler.runTaskLater(plugin, task, delay)
        return BukkitTask(task, scheduled)
    }

    override fun runAsync(task: Runnable): Task {
        TODO("not implemented")
    }

    override fun runAsync(task: Runnable, delay: Long): Task {
        TODO("not implemented")
    }

    override fun schedule(task: Runnable, delay: Long, interval: Long): Task {
        TODO("not implemented")
    }

}