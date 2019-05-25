package com.sucy.skill.facade.api.event

import com.sucy.skill.api.event.Event

/**
 * SkillAPIKotlin © 2018
 */
interface EventProxy<I : Event, T, E : T> {
    val targetType: Class<E>
    fun proxy(event: E): I
    fun proxy(event: I): E
    fun appliesTo(event: E): Boolean

    fun appliesTo(event: I): Boolean {
        return true
    }

    fun notify(event: T, handler: (I) -> Unit) {
        @Suppress("UNCHECKED_CAST")
        val cast = event as E
        if (appliesTo(cast)) {
            val proxied = proxy(cast)
            handler.invoke(proxied)
        }
    }
}