package com.sucy.skill.facade.api.managers

import com.sucy.skill.api.event.Cancellable

interface TaskManager {
    fun schedule(delay: Long, interval: Long, task: (Cancellable) -> Unit): Task
    fun run(delay: Long, task: () -> Unit): Task
    fun runAsync(task: () -> Unit): Task
    fun runAsync(delay: Long, task: () -> Unit): Task
}