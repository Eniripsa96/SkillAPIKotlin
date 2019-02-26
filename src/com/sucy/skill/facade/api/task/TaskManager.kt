package com.sucy.skill.facade.api.task

abstract class TaskManager {
    abstract fun schedule(task: Runnable, delay: Long, interval: Long): Task
    abstract fun run(task: Runnable, delay: Long): Task
    abstract fun runAsync(task: Runnable): Task
    abstract fun runAsync(task: Runnable, delay: Long): Task
}