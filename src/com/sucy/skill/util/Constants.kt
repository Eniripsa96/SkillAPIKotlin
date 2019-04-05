package com.sucy.skill.util

import com.sucy.skill.facade.api.managers.Task

object Constants {
    val NO_OP_TASK = object : Task {
        override var cancelled: Boolean
            get() = false
            set(_) {}
        override val runnable: () -> Unit = { }
    }
}