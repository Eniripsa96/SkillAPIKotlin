package com.sucy.skill.facade.bukkit.event

import com.google.common.collect.ImmutableMap
import com.sucy.skill.api.event.Step
import com.sucy.skill.facade.api.event.EventBusProxy
import com.sucy.skill.facade.api.event.EventProxy
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent
import com.sucy.skill.facade.api.event.player.AsyncPlayerLoginEvent
import com.sucy.skill.facade.api.event.player.PlayerJoinEvent
import com.sucy.skill.facade.api.event.player.PlayerQuitEvent
import com.sucy.skill.facade.bukkit.event.proxy.actor.BukkitActorDamagedByActorEventProxy
import com.sucy.skill.facade.bukkit.event.proxy.actor.BukkitActorDeathEventProxy
import com.sucy.skill.facade.bukkit.event.proxy.player.BukkitAsyncPlayerLoginEventProxy
import com.sucy.skill.facade.bukkit.event.proxy.player.BukkitPlayerJoinEventProxy
import com.sucy.skill.facade.bukkit.event.proxy.player.BukkitPlayerQuitEventProxy
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class BukkitEventBusProxy(private val plugin: JavaPlugin) : EventBusProxy<Event>(), Listener {
    override fun <I : com.sucy.skill.api.event.Event, E : Event> register(
            step: Step,
            proxy: EventProxy<I, Event, E>,
            handler: (event: I) -> Unit
    ) {
        plugin.server.pluginManager.registerEvent(
                proxy.targetType,
                this,
                stepMappings[step],
                { _: Listener, event: Event -> proxy.notify(event, handler) },
                plugin,
                true
        )
    }

    override fun invoke(event: Event) {
        plugin.server.pluginManager.callEvent(event)
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
        // Actor Events
        add(ActorDamagedByActorEvent::class, BukkitActorDamagedByActorEventProxy)
        add(ActorDeathEvent::class, BukkitActorDeathEventProxy)

        // Player Events
        add(AsyncPlayerLoginEvent::class, BukkitAsyncPlayerLoginEventProxy)
        add(PlayerJoinEvent::class, BukkitPlayerJoinEventProxy)
        add(PlayerQuitEvent::class, BukkitPlayerQuitEventProxy)
    }
}