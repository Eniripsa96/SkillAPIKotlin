package com.sucy.skill.util

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
