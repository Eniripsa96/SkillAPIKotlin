package com.sucy.skill.facade.bukkit

import com.sucy.skill.facade.api.Scheduler
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class BukkitScheduler(val plugin: Plugin) : Scheduler {

    override fun run(delayInTicks: Long, task: () -> Unit) {
        Bukkit.getScheduler().runTaskLater(plugin, task, delayInTicks)
    }

    override fun run(delayInTicks: Long, intervalInTicks: Long, task: () -> Unit) {
        Bukkit.getScheduler().runTaskTimer(plugin, task, delayInTicks, intervalInTicks)
    }

    override fun runAsnyc(task: () -> Unit) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, task)
    }

    override fun runAsync(delayInMillis: Long, task: () -> Unit) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delayInMillis / 50)
    }

    override fun runAsync(delayInMillis: Long, intervalInMillis: Long, task: () -> Unit) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delayInMillis / 50, intervalInMillis / 50)
    }

    override fun clearTasks() {
        Bukkit.getScheduler().cancelTasks(plugin)
    }
}