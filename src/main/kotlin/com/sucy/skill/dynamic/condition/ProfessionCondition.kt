package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player

class ProfessionCondition : Condition() {
    override val key = "profession"

    private var profession = "profession"
    private var exact = true

    override fun initialize() {
        super.initialize()
        profession = metadata.getString("profession", profession)
        exact = metadata.getBoolean("exact", exact)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return recipient is Player && recipient.activeAccount.professionSet.any { it.matches(profession, exact) }
    }
}