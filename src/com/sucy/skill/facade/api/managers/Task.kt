package com.sucy.skill.facade.api.managers

import com.sucy.skill.api.event.Cancellable

interface Task : Cancellable {
    val runnable: Runnable
    fun run() = runnable.run()
    fun cancel()
}