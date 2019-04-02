package com.sucy.skill.dynamic.trigger

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Cancellable
import com.sucy.skill.api.event.Event
import com.sucy.skill.api.event.Step
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.DynamicSkill
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor
import java.util.*
import java.util.function.Consumer
import kotlin.reflect.full.starProjectedType

/**
 * Possible triggers for dynamic skill effects
 */
abstract class Trigger<E : Event> : Effect() {
    override val type = EffectType.TRIGGER
    var running = false
        private set

    private val active = HashMap<UUID, Int>()

    /**
     * @return class of the event related to the trigger
     */
    abstract val event: Class<E>

    /**
     * @param event event details
     * @param level the level of the owning skill
     * @return true if the skill should activate, false otherwise
     */
    abstract fun shouldTrigger(event: E, level: Int): Boolean

    /**
     * Reads data from the event and provides values to the caster's value data. This can be used within
     * skills for more flexible effects. An example of this in base triggers is the Launch trigger providing
     * the speed a projectile was launched so mechanics can replace it with equally-fast projectiles.
     *
     * @param event event details
     * @param target entity to set values for
     */
    open fun setValues(event: E, target: Actor) { }

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
     * @return the one being affected by the trigger (initial target)
     */
    abstract fun getTarget(event: E): Actor?

    /**
     * Handles applying other effects after the skill resolves
     *
     * @param event event details
     * @param skill skill to resolve
     */
    open fun postProcess(event: E, skill: DynamicSkill) {}

    override fun initialize() {
        SkillAPI.eventBus.register(
                source = SkillAPI.plugin,
                event = this.event.kotlin.starProjectedType,
                handler = Consumer { applyEvent(it) },
                step = Step.LAST,
                ignoreCancelled = true)
    }

    private fun applyEvent(event: Event) {
        event as E

        val caster = getCaster(event)
        if (caster == null || !active.containsKey(caster.uuid)) return

        val level = active.getValue(caster.uuid)
        if (!shouldTrigger(event, level)) return

        val target = getTarget(event) ?: return

        setValues(event, caster)
        execute(
                CastContext(level, caster, if (event is Cancellable) event else null),
                listOf(target))
    }

    fun trigger(caster: Actor, target: Actor, level: Int): Boolean {
        return execute(
                CastContext(level, caster, null),
                listOf(target))
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        try {
            running = true
            return executeChildren(context, targets)
        } finally {
            running = false
        }
    }

    fun init(actor: Actor, level: Int) {
        active[actor.uuid] = level
    }

    override fun doCleanUp(caster: Actor) {
        active.remove(caster.uuid)
    }
}
