package com.sucy.skill.dynamic.trigger

import com.google.common.collect.ImmutableList
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.dynamic.DynamicSkill
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.util.io.Data

/**
 * Possible triggers for dynamic skill effects
 */
abstract class Trigger<E : Event> : Effect() {
    override val type = EffectType.TRIGGER
    var running = false
        private set

    /**
     * @return class of the event related to the trigger
     */
    abstract val event: Class<E>

    /**
     * @param event event details
     * @param level the level of the owning skill
     * @param settings skill settings
     * @return true if the skill should activate, false otherwise
     */
    abstract fun shouldTrigger(event: E, level: Int): Boolean

    /**
     * Reads data from the event and provides values to the caster's value data. This can be used within
     * skills for more flexible effects. An example of this in base triggers is the Launch trigger providing
     * the speed a projectile was launched so mechanics can replace it with equally-fast projectiles.
     *
     * @param event event details
     * @param data caster's value data to populate
     */
    abstract fun setValues(event: E, data: MutableMap<String, Any>)

    /**
     * Fetches the caster as determined by the triggering event.
     *
     * @param event event details
     * @return the one to apply the trigger for
     */
    abstract fun getCaster(event: E): Actor?

    /**
     * Fetches the target as determined by the triggering event. This can be the same as the caster.
     *
     * @param event event details
     * @param settings skill settings
     * @return the one being affected by the trigger (initial target)
     */
    abstract fun getTarget(event: E, settings: Data): Actor?

    /**
     * Handles applying other effects after the skill resolves
     *
     * @param event event details
     * @param skill skill to resolve
     */
    open fun postProcess(event: E, skill: DynamicSkill) {}

    override fun initialize() { }

    fun trigger(caster: Actor, target: Actor, level: Int): Boolean {
        return execute(caster, level, ImmutableList.of(target))
    }

    override fun execute(caster: Actor, level: Int, targets: List<Actor>): Boolean {
        try {
            running = true
            return executeChildren(caster, level, targets)
        } finally {
            running = false
        }
    }
}
