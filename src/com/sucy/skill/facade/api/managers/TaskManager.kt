package com.sucy.skill.facade.api.managers

interface TaskManager {
    fun schedule(task: Runnable, delay: Long, interval: Long): Task
    fun run(task: Runnable, delay: Long): Task
    fun runAsync(task: Runnable): Task
    fun runAsync(task: Runnable, delay: Long): Task
}