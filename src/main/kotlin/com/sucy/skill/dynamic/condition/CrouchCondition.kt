package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player

class CrouchCondition : Condition() {
    override val key = "crouch"

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return target is Player && target.isCrouching
    }
}