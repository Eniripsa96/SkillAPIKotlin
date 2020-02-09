package com.sucy.skill.api.event

import com.sucy.skill.facade.api.event.EventBusProxy
import java.util.*
import java.util.function.Consumer
import kotlin.collections.mutableMapOf
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.starProjectedType

/**
 * SkillAPIKotlin © 2018
 */
class EventBus(private val proxy: EventBusProxy<*>) {
    private val registry = mutableMapOf<KType, MutableMap<Step, MutableCollection<HandlerContext<*>>>>()

    fun registerEvents() = proxy.registerEvents()

    /**
     * Registers all event handlers in [listener] denoted with [Listen] annotations
     * for the given [source]. Handlers will continue to receive events until [unregister]
     * is called for the same [source].
     */
    fun register(source: Any, listener: Any) {
        getAnnotations(listener) { func, listen ->
            val handler = Consumer { event: Event -> func.call(listener, event) }
            register(source, func.parameters[1].type, handler, listen.step, listen.ignoreCancelled)
        }
    }

    /**
     * Gets all the function/Listen annotation pairs from the object
     */
    internal fun getAnnotations(listener: Any, handler: (KFunction<*>, Listen) -> Unit) {
        listener::class.memberFunctions.forEach { func ->
            func.annotations.forEach { annotation ->
                val isListener = annotation.annotationClass == Listen::class
                val hasOneParameter = func.parameters.size == 2 // one instance parameter, one actual
                if (isListener && hasOneParameter && func.parameters[1].type.isSubtypeOf(Event::class.starProjectedType)) {
                    val listen = annotation as Listen
                    handler(func, listen)
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
        val byStep = registry.computeIfAbsent(event) { mutableMapOf() }
        val handlers = byStep.computeIfAbsent(step) { mutableListOf() }
        handlers.add(HandlerContext(source, handler, step, ignoreCancelled))
    }

    /**
     * Triggers the [event] for all registered handlers.
     */
    fun <T : Event> trigger(event: T): T = proxy.invoke(event)

    fun trigger(event: Event, step: Step) {
        val handlers = registry[event::class.starProjectedType]?.get(step)
        handlers?.forEach { it.apply(event) }
    }

    /**
     * Unregisters all handlers that were registered with the [source]
     */
    fun unregister(source: Any) {
        registry.values.forEach { byType ->
            byType.values.forEach { handlers ->
                handlers.removeIf { it.source == source }
            }
        }
    }

    private data class HandlerContext<T : Event>(
            internal val source: Any,
            private val handler: Consumer<T>,
            private val step: Step,
            private val ignoreCancelled: Boolean
    ) {
        fun apply(event: Event) {
            if (!(ignoreCancelled && event is Cancellable && event.cancelled)) {
                @Suppress("UNCHECKED_CAST")
                handler.accept(event as T)
            }
        }
    }
}
