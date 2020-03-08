package com.sucy.skill.util.collection

class BiMap<K, V>() {
    private val map = mutableMapOf<K, V>()
    private val reversed = mutableMapOf<V, K>()

    operator fun get(key: K): V? = map[key]
    operator fun set(key: K, value: V) {
        map[key] = value
        reversed[value] = key
    }

    fun reversed(): Map<V, K> = reversed

    fun computeIfAbsent(key: K, compute: (K) -> V): V {
        return map.computeIfAbsent(key) {
            val value = compute(it)
            reversed[value] = key
            value
        }
    }
}