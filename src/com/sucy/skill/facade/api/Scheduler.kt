package com.sucy.skill.facade.api

interface Scheduler {

    fun run(task: () -> Unit) = run(0, task)
    fun run(delayInTicks: Long, task: () -> Unit)
    fun run(delayInTicks: Long, intervalInTicks: Long, task: () -> Unit)

    fun runAsnyc(task: () -> Unit)
    fun runAsync(delayInMillis: Long, task: () -> Unit)
    fun runAsync(delayInMillis: Long, intervalInMillis: Long, task: () -> Unit)

    fun clearTasks()
}