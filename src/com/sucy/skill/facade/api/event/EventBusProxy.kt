package com.sucy.skill.facade.api.event

import com.sucy.skill.api.event.Event
import com.sucy.skill.api.event.Step

/**
 * SkillAPIKotlin © 2018
 */
interface EventBusProxy<T> {
    val proxies: Map<Class<*>, EventProxy<*, T, *>>

    fun <E : Event> register(eventType: Class<E>, step: Step, handler: (event: E) -> Unit): Boolean
    fun <E : Event> invoke(event: E) : Boolean
}