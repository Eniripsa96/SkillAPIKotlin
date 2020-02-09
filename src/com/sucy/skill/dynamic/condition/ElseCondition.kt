package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

class ElseCondition : Effect() {
    override val type = EffectType.CONDITION
    override val key = "else"

    var legacy: Boolean = false

    override fun initialize() {
        legacy = metadata.getBoolean("legacy", legacy)
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        val applicable = context.failures.pop()
        context.failures.push(emptyList())
        
        return if (legacy && !context.last) executeChildren(context, targets)
        else executeChildren(context, applicable)
    }
}