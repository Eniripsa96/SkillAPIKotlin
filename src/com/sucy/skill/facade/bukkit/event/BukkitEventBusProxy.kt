package com.sucy.skill.facade.bukkit.event

import com.google.common.collect.ImmutableMap
import com.sucy.skill.api.event.Step
import com.sucy.skill.facade.api.event.EventBusProxy
import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.bukkit.event.actor.BukkitActorDamagedByActorEventProxy
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class BukkitEventBusProxy(private val plugin: JavaPlugin) : EventBusProxy<Event>, Listener {
    override val proxies = HashMap<Class<*>, EventProxy<*, Event, *>>()

    override fun <E : com.sucy.skill.api.event.Event> register(eventType: Class<E>, step: Step, handler: (event: E) -> Unit): Boolean {
        val proxy = proxies[eventType] ?: return false

        @Suppress("UNCHECKED_CAST")
        val typedProxy = proxy as EventProxy<E, Event, Event>

        plugin.server.pluginManager.registerEvent(
                proxy.targetType,
                this,
                stepMappings[step],
                { _: Listener, event: Event -> typedProxy.notify(event, handler) },
                plugin,
                true
        )

        return true
    }

    override fun <E : com.sucy.skill.api.event.Event> invoke(event: E) : E {
        val proxy = proxies[event::class.java] ?: return event

        @Suppress("UNCHECKED_CAST")
        val typedProxy = proxy as EventProxy<E, Event, Event>

        val proxied = typedProxy.proxy(event)
        plugin.server.pluginManager.callEvent(proxied)
        return typedProxy.proxy(proxied)
    }

    private val stepMappings = EnumMap(ImmutableMap.builder<Step, EventPriority>()
            .put(Step.FIRST, EventPriority.LOWEST)
            .put(Step.EARLY, EventPriority.LOW)
            .put(Step.NORMAL, EventPriority.NORMAL)
            .put(Step.LATE, EventPriority.HIGH)
            .put(Step.LAST, EventPriority.HIGHEST)
            .put(Step.REACT, EventPriority.MONITOR)
            .build())

    init {
        proxies[ActorDamagedByActorEvent::class.java] = BukkitActorDamagedByActorEventProxy
    }
}