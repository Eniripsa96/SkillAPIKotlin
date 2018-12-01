package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDamagedEvent
import com.sucy.skill.util.io.Data

/**
 * SkillAPI © 2018
 */
class SkillTakenTrigger : BaseDamageTrigger() {
    override val requireSkillDamage = false

    /** {@inheritDoc}  */
    override val key: String
        get() = "TOOK_SKILL_DAMAGE"

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDamagedEvent): Actor? {
        return event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDamagedEvent, settings: Data): Actor? {
        return if (isUsingTarget(settings)) event.attacker else event.actor
    }
}
