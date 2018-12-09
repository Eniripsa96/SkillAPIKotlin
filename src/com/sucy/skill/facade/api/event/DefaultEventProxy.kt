package com.sucy.skill.facade.api.event

import com.sucy.skill.api.event.Event

/**
 * SkillAPIKotlin © 2018
 */
class DefaultEventProxy<I : Event, T, E : T>(
        override val targetType: Class<E>,
        private val inConverter: (E) -> I,
        private val outConverter: (I) -> E,
        private val inFilter: (E) -> Boolean = { true }
) : EventProxy<I, T, E> {

    override fun appliesTo(event: E): Boolean {
        return inFilter.invoke(event)
    }

    override fun proxy(event: E): I {
        return inConverter.invoke(event)
    }

    override fun proxy(event: I): E {
        return outConverter.invoke(event)
    }
}