package com.sucy.skill.facade.api.event

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Event
import com.sucy.skill.api.event.Step

/**
 * SkillAPIKotlin © 2018
 */
interface EventBusProxy<T> {
    val proxies: MutableMap<Class<*>, EventProxy<*, T, *>>

    /**
     * Registers the [handler] for the external event system at the specified [step].
     */
    fun <E : Event> register(eventType: Class<E>, step: Step, handler: (event: E) -> Unit): Boolean

    /**
     * Invokes the [event] for the external system
     */
    fun <E : Event> invoke(event: E) : E

    /**
     * Sets up the proxy for the given [eventProxy], attaching it to the external
     * event system and wiring up triggers to the internal event bus.
     */
    fun <E : Event> proxy(eventType: Class<E>, eventProxy: EventProxy<E, T, *>) {
        Step.values().forEach { step ->
            register(eventType, step) { SkillAPI.eventBus.trigger(it, step) }
        }
        proxies[eventType] = eventProxy
    }
}