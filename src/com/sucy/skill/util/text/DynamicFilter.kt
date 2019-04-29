package com.sucy.skill.util.text

import com.google.common.collect.ImmutableMap
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Entity
import kotlin.math.ceil
import kotlin.math.floor

object DynamicFilter {

    fun apply(text: String, caster: Actor, target: Actor): String {
        val builder = StringBuilder()
        var start = 0
        var open = text.indexOf('{')
        while (open >= 0) {
            val close = text.indexOf('}', open + 2)
            if (close > 0) {
                // Apply the filter if it has a value for the key
                val replace = getFilterValue(text.substring(open + 1, close), caster, target)
                if (replace != null) {
                    builder.append(text.substring(start, open))
                    builder.append(replace)
                    start = close + 1
                }
            }
            open = text.indexOf('{', close + 1)
        }
        if (start == 0) return text

        builder.append(text.substring(start))
        return builder.toString()
    }

    private fun getFilterValue(filter: String, caster: Actor, target: Actor): String? {
        return when {
            filter.startsWith("target.") -> getValue(target, filter.substring(7))
            filter == "target" -> getValue(target, "name")
            else -> getValue(caster, filter)
        }
    }

    private fun getValue(actor: Actor, key: String): String? {
        val common = FILTER_KEYS[key]
        return common?.invoke(actor) ?: when {
            actor.metadata.containsKey(key) -> format(actor.metadata.getValue(key))
            actor.values.has(key) -> actor.values[key].total.toString()
            else -> null
        }
    }

    private fun format(meta: Any): String {
        return when (meta) {
            is Entity -> meta.name
            is Location -> "${meta.coords.x.toInt()}, ${meta.coords.y.toInt()}, ${meta.coords.z.toInt()}"
            else -> meta.toString()
        }
    }

    private val FILTER_KEYS = ImmutableMap.builder<String, (Actor) -> String>()
            .put("name") { it.name }
            .put("health") { ceil(it.health).toString() }
            .put("maxHealth") { ceil(it.maxHealth).toString() }
            .put("mana") { floor(it.mana).toString() }
            .put("maxMana") { floor(it.maxMana).toString() }
            .put("lvl") { it.level.toString() }
            .put("level") { it.level.toString() }
            .put("caster") { it.name }
            .build()
}