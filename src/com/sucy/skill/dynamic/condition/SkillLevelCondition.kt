package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class SkillLevelCondition : AbstractNumberCondition() {
    override val key = "skill level"

    private var skill = ""

    override fun initialize() {
        super.initialize()
        skill = metadata.getString("skill", skill)
    }

    override fun getValue(actor: Actor, context: CastContext, target: Actor): Double {
        return if (skill.isBlank()) context.level.toDouble()
        else actor.skills[skill]?.level?.toDouble() ?: 0.0
    }
}