package com.sucy.skill.facade.bukkit.managers

import com.sucy.skill.facade.api.managers.Task
import org.bukkit.scheduler.BukkitTask

class BukkitTask(override val runnable: Runnable, private val bukkit: BukkitTask) : Task {
    override fun cancel() = bukkit.cancel()
}