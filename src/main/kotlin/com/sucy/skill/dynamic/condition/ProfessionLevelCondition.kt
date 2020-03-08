package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player

class ProfessionLevelCondition : AbstractNumberCondition() {
    override val key = "profession level"

    private var group = "any"

    override fun initialize() {
        super.initialize()
        group = metadata.getString("group", group).toLowerCase()
    }

    override fun getValue(actor: Actor, context: CastContext, target: Actor): Double {
        return when {
            actor is Player && group == "any" -> actor.activeAccount.professionSet.map { it.level }.max() ?: 0
            actor is Player -> actor.activeAccount.professionSet[group]?.level ?: 0
            // TODO - grab level from other plugins
            else -> 0
        }.toDouble()
    }
}