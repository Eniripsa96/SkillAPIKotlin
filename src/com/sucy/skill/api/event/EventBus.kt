package com.sucy.skill.api.event

import com.sucy.skill.facade.api.event.EventBusProxy
import java.util.*
import java.util.function.Consumer
import kotlin.collections.HashMap
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.starProjectedType

/**
 * SkillAPIKotlin © 2018
 */
class EventBus(private val proxy: EventBusProxy<*>) {
    private val registry = HashMap<KType, MutableCollection<HandlerContext<*>>>()

    /**
     * Registers all event handlers in [listener] denoted with [Listen] annotations
     * for the given [source]. Handlers will continue to receive events until [unregister]
     * is called for the same [source].
     */
    fun register(source: Any, listener: Any) {
        listener::class.memberFunctions.forEach { func ->
            func.annotations.forEach { annotation ->
                if (annotation.annotationClass == Listen::class &&
                        func.parameters.size == 1 &&
                        func.parameters[0].type.isSubtypeOf(Event::class.starProjectedType)) {
                    val listen = annotation as Listen
                    val handler = Consumer { event: Event -> func.call(event) }
                    register(source, func.parameters[0].type, handler, listen.step, listen.ignoreCancelled)
                }
            }
        }
    }

    /**
     * Registers the [handler] to receive any events of type [event]. Ordering of receiving
     * the event is determined by the [step]. If the event is cancelled before the event
     * is received by the [handler] and [ignoreCancelled] is true, then it will be skipped.
     * Handlers will continue to receive events until [unregister] is called for the same [source].
     */
    fun <T : Event> register(source: Any, event: KType, handler: Consumer<T>, step: Step = Step.NORMAL, ignoreCancelled: Boolean = true) {
        if (!proxy.register())
        val handlers = registry.computeIfAbsent(event) { TreeSet() }
        handlers.add(HandlerContext(source, handler, step, ignoreCancelled))
    }

    /**
     * Triggers the [event] for all registered handlers.
     */
    fun trigger(event: Event) {
        val handlers = registry[event::class.starProjectedType]
        handlers?.forEach { it.apply(event) }
    }

    /**
     * Unregisters all handlers that were registered with the [source]
     */
    fun unregister(source: Any) {
        registry.keys.forEach { type ->
            registry.compute(type) { _, v -> v?.filterTo(ArrayList()) { it.source == source } }
        }
    }

    private data class HandlerContext<T : Event>(
            internal val source: Any,
            private val handler: Consumer<T>,
            private val step: Step,
            private val ignoreCancelled: Boolean
    ) : Comparable<HandlerContext<T>> {

        override fun compareTo(other: HandlerContext<T>): Int {
            return Integer.compare(step.ordinal, other.step.ordinal)
        }

        fun apply(event: Event) {
            if (!(ignoreCancelled && event is Cancellable && event.cancelled)) {
                @Suppress("UNCHECKED_CAST")
                handler.accept(event as T)
            }
        }
    }
}
