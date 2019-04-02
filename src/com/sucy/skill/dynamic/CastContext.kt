package com.sucy.skill.dynamic

import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.facade.api.entity.Actor

data class CastContext(val level: Int, val caster: Actor, val cancellable: Cancellable?) {
    fun cancel() {
        cancellable?.cancelled = true
    }
}