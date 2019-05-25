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
class SpongeEventBusProxy(private val plugin: Any) : EventBusProxy<Event>() {
    override fun <I : com.sucy.skill.api.event.Event, E : Event> register(
            step: Step,
            proxy: EventProxy<I, Event, E>,
            handler: (event: I) -> Unit
    ) {
        Sponge.getEventManager().registerListener(
                plugin,
                proxy.targetType,
                stepMappings[step]!!
        ) { event: Event -> proxy.notify(event, handler) }
    }

    override fun invoke(event: Event) {
        Sponge.getEventManager().post(event)
    }

    override fun registerProxies() {
        TODO("not implemented")
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
