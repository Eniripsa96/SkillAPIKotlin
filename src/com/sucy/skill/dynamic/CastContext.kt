package com.sucy.skill.dynamic

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.facade.api.entity.Actor
import java.util.*

data class CastContext(
        val level: Int,
        val caster: Actor,
        val cancellable: Cancellable?,
        var last: Boolean = false,
        val failures: Deque<List<Actor>> = ArrayDeque<List<Actor>>()
) {
    fun cancel() {
        cancellable?.cancelled = true
    }
}