package com.sucy.skill.util.io

import com.google.common.collect.ImmutableSet
import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.internal.data.InternalItem
import com.sucy.skill.util.math.formula.DynamicFormula
import com.sucy.skill.util.text.color
import java.util.stream.Collectors

/**
 * SkillAPIKotlin © 2018
 */
class Data internal constructor() {
    val comments = HashMap<String, MutableList<String>>()
    private val data = HashMap<String, Any>()
    private val formulas = HashMap<String, DynamicFormula>()
    private val keys = ArrayList<String>()

    constructor(initial: Map<String, Any>) : this() {
        initial.forEach { key, value ->
            @Suppress("UNCHECKED_CAST")
            data[key] = if (value is Map<*, *>) Data(value as Map<String, Any>) else value
        }
    }

    fun getVersion(): Int {
        return getInt(VERSION_KEY, 1)
    }

    fun setVersion(version: Int) {
        set(VERSION_KEY, version)
        addComment(VERSION_KEY, "DATA VERSION - DO NOT MODIFY OR REMOVE")
    }

    fun keys(): List<String> {
        return ArrayList(keys)
    }

    fun clear() {
        comments.clear()
        data.clear()
        keys.clear()
    }

    fun addComment(key: String, comment: String) {
        comments.computeIfAbsent(key) { ArrayList() }.add(comment)
    }

    fun has(key: String): Boolean {
        return get(key) != null
    }

    fun set(key: String, value: Number) {
        if (!keys.contains(key)) keys.add(key)
        data[key] = value
    }

    fun set(key: String, value: String) {
        if (!keys.contains(key)) keys.add(key)
        data[key] = value
    }

    fun set(key: String, value: Boolean) {
        if (!keys.contains(key)) keys.add(key)
        data[key] = value
    }

    fun set(key: String, value: List<String>) {
        if (!keys.contains(key)) keys.add(key)
        data[key] = value
    }

    fun set(key: String, value: Data) {
        if (!keys.contains(key)) keys.add(key)
        data[key] = value
    }

    fun remove(key: String) {
        keys.remove(key)
        data.remove(key)
    }

    fun getSection(key: String): Data? {
        val section = get(key)
        return if (section is Data) section else null
    }

    fun createSection(key: String): Data {
        val section = Data()
        data.put(key, section)
        if (!keys.contains(key)) keys.add(key)
        return section
    }

    fun getOrCreateSection(key: String): Data {
        val index = key.indexOf('.')
        return if (index > 0) {
            getOrCreateSection(key.substring(0, index))
                    .getOrCreateSection(key.substring(index + 1))
        } else {
            return getSection(key) ?: createSection(key)
        }
    }

    fun getString(key: String): String? {
        return get(key)?.toString()
    }

    fun getString(key: String, fallback: String): String {
        return get(key)?.toString() ?: fallback
    }

    fun getBoolean(key: String, fallback: Boolean = false): Boolean {
        return TRUE_TERMS.contains(getString(key, fallback.toString()).toLowerCase())
    }

    fun getInt(key: String, fallback: Int = 0): Int {
        return try {
            get(key)?.toString()?.toInt() ?: fallback
        } catch (ex: NumberFormatException) {
            fallback
        }
    }

    fun getDouble(key: String, fallback: Double = 0.0): Double {
        return try {
            get(key)?.toString()?.toDouble() ?: fallback
        } catch (ex: NumberFormatException) {
            fallback
        }
    }

    fun getFormula(key: String, fallback: Double = 0.0): DynamicFormula {
        return formulas.computeIfAbsent(key) { DynamicFormula(getString(key, fallback.toString())) }
    }

    fun getStringList(key: String, fallback: List<String> = emptyList()): List<String> {
        val found = get(key)
        @Suppress("UNCHECKED_CAST")
        return if (found is List<*>) found as List<String> else fallback
    }

    fun getItem(key: String): Item {
        val data = getOrCreateSection(key)
        return InternalItem(
                data.getString("type", "DIRT").toUpperCase().replace(' ', '_'),
                data.getInt("durability").toShort(),
                data.getInt("data").toByte(),
                data.getInt("amount", 1),
                data.getString("name")?.color(),
                data.getStringList("lore").color())
    }

    fun get(key: String, fallback: Any? = null): Any? {
        val index = key.indexOf('.')
        return if (index >= 0) {
            val section = getSection(key.substring(0, index))
            if (section == null) {
                fallback
            } else {
                section.get(key.substring(index + 1))
            }
        } else if (data.containsKey(key)) {
            data[key]
        } else {
            fallback
        }
    }

    fun asMap(): Map<String, Any> {
        return data.entries.stream()
                .collect(Collectors.toMap({ t: Map.Entry<String, Any> -> t.key })
                { t: Map.Entry<String, Any> -> if (t.value is Data) (t.value as Data).asMap() else t.value })
    }

    fun applyDefaults(defaults: Data) {
        defaults.data.forEach { key, value ->
            if (defaults.comments.containsKey(key)) {
                defaults.comments[key]?.let { comments.put(key, it) }
            }
            if (value is Data) {
                getOrCreateSection(key).applyDefaults(value)
            } else {
                data.putIfAbsent(key, value)
            }
        }
    }

    fun trim(defaults: Data) {
        keys.forEach {
            if (!defaults.data.containsKey(it)) {
                remove(it)
            } else if (defaults.data[it] is Data) {
                getOrCreateSection(it).trim(defaults.data[it] as Data)
            }
        }
    }

    private companion object {
        const val VERSION_KEY = "_v"
        val TRUE_TERMS: Set<String> = ImmutableSet.of(
                "true",
                "t",
                "y",
                "yes",
                "1",
                "+"
        )
    }
}