package com.sucy.skill.facade.bukkit.managers

import com.sucy.skill.facade.api.managers.Task
import org.bukkit.scheduler.BukkitTask

data class BukkitTask(private val bukkit: BukkitTask, override val runnable: () -> Unit) : Task {
    override var cancelled: Boolean
            get() = bukkit.isCancelled
            set(value) {
                if (value) bukkit.cancel()
                else throw IllegalStateException("Cannot undo cancelling a task")
            }
}