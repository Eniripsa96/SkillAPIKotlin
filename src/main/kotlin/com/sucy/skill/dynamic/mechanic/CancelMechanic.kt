package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

class CancelMechanic : Effect() {
    override val key = "cancel"
    override val type = EffectType.MECHANIC

    override fun initialize() { }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        context.cancellable?.cancelled = true
        return context.cancellable != null
    }
}
