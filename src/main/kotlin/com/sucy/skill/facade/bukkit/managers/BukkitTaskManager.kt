package com.sucy.skill.facade.bukkit.managers

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.facade.api.managers.Task
import com.sucy.skill.facade.api.managers.TaskManager
import com.sucy.skill.facade.bukkit.SkillAPIBukkit
import org.bukkit.Bukkit

class BukkitTaskManager(val plugin: SkillAPIBukkit) : TaskManager {
    override fun run(delay: Long, task: () -> Unit): Task {
        val scheduled = Bukkit.getServer().scheduler.runTaskLater(plugin, task, delay)
        return BukkitTask(scheduled, task)
    }

    override fun runAsync(task: () -> Unit): Task {
        val scheduled = Bukkit.getServer().scheduler.runTaskAsynchronously(plugin, task)
        return BukkitTask(scheduled, task)
    }

    override fun runAsync(delay: Long, task: () -> Unit): Task {
        val scheduled = Bukkit.getServer().scheduler.runTaskLaterAsynchronously(plugin, task, delay)
        return BukkitTask(scheduled, task)
    }

    override fun schedule(delay: Long, interval: Long, task: (Cancellable) -> Unit): Task {
        var cancellable: Cancellable? = null
        val runnable: () -> Unit = { cancellable?.let(task) }
        val scheduled = Bukkit.getServer().scheduler.runTaskTimer(plugin, runnable, delay, interval)
        cancellable = BukkitTask(scheduled, runnable)
        return cancellable
    }
}