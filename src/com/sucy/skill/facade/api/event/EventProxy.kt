package com.sucy.skill.facade.api.event

import com.sucy.skill.api.event.Event

/**
 * SkillAPIKotlin © 2018
 */
interface EventProxy<I : Event, T, E : T> {
    val targetType: Class<E>
    fun proxy(event: E): I
    fun proxy(event: I): E

    fun notify(event: T, handler: (event: I) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        val proxied = proxy(event as E)
        handler.invoke(proxied)
    }
}