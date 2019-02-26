package com.sucy.skill.facade.api.task

interface Task {
    val runnable: Runnable
    fun run()
    fun cancel()
}