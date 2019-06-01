package com.sucy.skill.util

import com.sucy.skill.SkillAPI
import com.sucy.skill.SkillAPIPlatform
import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.Scheduler
import com.sucy.skill.facade.api.Server
import com.sucy.skill.facade.api.managers.PlayerManager
import com.sucy.skill.util.text.enumName
import kotlin.reflect.KClass

private val enumCache: MutableMap<KClass<*>, Map<String, Enum<*>>> = HashMap()

fun <T : Enum<*>> KClass<T>.match(name: String?, default: T): T {
    return name?.let { byName.getOrDefault(it.enumName(), default) } ?: default
}

val <T : Enum<*>> KClass<T>.byName: Map<String, T>
    get() {
        return enumCache.getOrPut(this) {
            java.enumConstants.map { it.name to it }.toMap()
        } as Map<String, T>
    }

val plugin: SkillAPIPlatform
    get() {
        return SkillAPI.plugin
    }

val scheduler: Scheduler
    get() {
        return SkillAPI.scheduler
    }

val players: PlayerManager
    get() {
        return SkillAPI.server.players
    }

val server: Server
    get() {
        return SkillAPI.server
    }

fun <T : Event> callEvent(event: T): T {
    return SkillAPI.eventBus.trigger(event)
}
