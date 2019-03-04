package com.sucy.skill.facade.api.managers

interface Task {
    val runnable: Runnable
    fun run() = runnable.run()
    fun cancel()
}