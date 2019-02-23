package com.sucy.skill.dynamic.trigger

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.util.io.Data
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashSet

/**
 * SkillAPIKotlin © 2018
 */
class DamageTrigger : Trigger<ActorDamagedByActorEvent>() {
    private var min = 0.0
    private var max = 1.0e10
    private var attacker = false
    private var sources = Arrays.stream(ActorDamagedByActorEvent.DamageSource.values()).collect(Collectors.toSet())

    /** {@inheritDoc}  */
    override val key: String
        get() = "DAMAGE"

    /** {@inheritDoc}  */
    override val event: Class<ActorDamagedByActorEvent>
        get() = ActorDamagedByActorEvent::class.java

    override fun initialize() {
        min = metadata.getDouble("min", min)
        max = metadata.getDouble("max", max)
        attacker = metadata.getBoolean("attacker", attacker)
        sources = metadata.getStringList("source")
                .mapTo(HashSet()) { ActorDamagedByActorEvent.DamageSource.valueOf(it) }
                .ifEmpty { sources }
    }

    /** {@inheritDoc}  */
    override fun shouldTrigger(event: ActorDamagedByActorEvent, level: Int): Boolean {
        return event.amount in min..max && sources.contains(event.source)
    }

    /** {@inheritDoc}  */
    override fun setValues(event: ActorDamagedByActorEvent, data: MutableMap<String, Any>) {
        data["api-taken"] = event.amount
        data["api-dealt"] = event.amount
    }

    /** {@inheritDoc}  */
    override fun getCaster(event: ActorDamagedByActorEvent): Actor? {
        return if (attacker) event.attacker else event.actor
    }

    /** {@inheritDoc}  */
    override fun getTarget(event: ActorDamagedByActorEvent, settings: Data): Actor? {
        return if (attacker) event.actor else event.attacker
    }
}