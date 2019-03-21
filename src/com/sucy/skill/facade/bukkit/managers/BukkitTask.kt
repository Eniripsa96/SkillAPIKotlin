package com.sucy.skill.facade.bukkit.managers

import com.sucy.skill.facade.api.managers.Task
import org.bukkit.scheduler.BukkitTask

data class BukkitTask(override val runnable: Runnable, private val bukkit: BukkitTask) : Task {
    override var cancelled: Boolean
            get() = bukkit.isCancelled
            set(value) {
                if (value) cancel()
                else throw IllegalStateException("Cannot undo cancelling a task")
            }

    override fun cancel() = bukkit.cancel()
}