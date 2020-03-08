package com.sucy.skill.facade.api.managers

import com.sucy.skill.api.event.Cancellable

interface Task : Cancellable {
    val runnable: () -> Unit
    fun run() = runnable.invoke()
}