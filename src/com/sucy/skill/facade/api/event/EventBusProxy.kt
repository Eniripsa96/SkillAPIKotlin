package com.sucy.skill.facade.api.event

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Event
import com.sucy.skill.api.event.Step
import kotlin.reflect.KClass

/**
 * SkillAPIKotlin © 2018
 */
abstract class EventBusProxy<T> {
    val proxies = HashMap<Class<*>, MutableList<EventProxy<*, T, *>>>()

    /**
     * Registers the [handler] for the external event system at the specified [step].
     */
    abstract fun <I : Event, E : T> register(step: Step, proxy: EventProxy<I, T, E>, handler: (event: I) -> Unit)

    abstract fun invoke(event: T)

    /**
     * Invokes the [event] for the external system
     */
    fun <E : Event> invoke(event: E) : E {
        val proxyList = proxies[event::class.java] ?: return event

        @Suppress("UNCHECKED_CAST")
        val typedProxies = proxyList as List<EventProxy<E, T, T>>
        val typedProxy = typedProxies.find { it.appliesTo(event) } ?: return event

        val proxied = typedProxy.proxy(event)
        invoke(proxied)
        return typedProxy.proxy(proxied)
    }

    /**
     * Sets up the proxy for the given [proxy], attaching it to the external
     * event system and wiring up triggers to the internal event bus.
     */
    protected fun <I : Event, E : T> add(type: KClass<I>, proxy: EventProxy<I, T, E>) {
        Step.values().forEach { step ->
            register(step, proxy) { SkillAPI.eventBus.trigger(it, step) }
        }
        proxies.computeIfAbsent(type.java) { ArrayList() }.add(proxy)
    }
}