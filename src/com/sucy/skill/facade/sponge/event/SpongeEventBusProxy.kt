package com.sucy.skill.facade.sponge.event

import com.google.common.collect.ImmutableMap
import com.sucy.skill.api.event.Step
import com.sucy.skill.facade.api.event.EventBusProxy
import com.sucy.skill.facade.api.event.EventProxy
import org.spongepowered.api.Sponge
import org.spongepowered.api.event.Event
import org.spongepowered.api.event.Order
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class SpongeEventBusProxy(private val plugin: Any) : EventBusProxy<Event> {
    override val proxies = HashMap<Class<*>, EventProxy<*, Event, *>>()

    override fun <E : com.sucy.skill.api.event.Event> register(eventType: Class<E>, step: Step, handler: (event: E) -> Unit): Boolean {
        val proxy = proxies[eventType] ?: return false

        @Suppress("UNCHECKED_CAST")
        val typedProxy = proxy as EventProxy<E, Event, Event>

        Sponge.getEventManager().registerListener(
                plugin,
                proxy.targetType,
                stepMappings[step]!!
        ) { event: Event -> typedProxy.notify(event, handler) }

        return true
    }

    override fun <E : com.sucy.skill.api.event.Event> invoke(event: E): E {
        val proxy = proxies[event::class.java] ?: false

        @Suppress("UNCHECKED_CAST")
        val typedProxy = proxy as EventProxy<E, Event, Event>

        val proxied = typedProxy.proxy(event)
        Sponge.getEventManager().post(proxied)

        return typedProxy.proxy(proxied)
    }

    private val stepMappings = EnumMap(ImmutableMap.builder<Step, Order>()
            .put(Step.FIRST, Order.FIRST)
            .put(Step.EARLY, Order.EARLY)
            .put(Step.NORMAL, Order.DEFAULT)
            .put(Step.LATE, Order.LATE)
            .put(Step.LAST, Order.LAST)
            .put(Step.REACT, Order.POST)
            .build())
}
