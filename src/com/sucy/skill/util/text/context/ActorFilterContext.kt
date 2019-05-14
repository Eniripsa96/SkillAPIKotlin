package com.sucy.skill.util.text.context

import com.google.common.collect.ImmutableMap
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Entity
import kotlin.math.ceil
import kotlin.math.floor

class ActorFilterContext(
        key: String,
        subject: Actor
) : FilterContext<Actor>(key, subject, FILTERS, DEFAULT) {
    companion object {
        private val FILTERS = ImmutableMap.builder<String, (Actor) -> String>()
                .put("name") { it.name }
                .put("health") { ceil(it.health).toString() }
                .put("maxHealth") { ceil(it.maxHealth).toString() }
                .put("mana") { floor(it.mana).toString() }
                .put("maxMana") { floor(it.maxMana).toString() }
                .put("lvl") { it.level.toString() }
                .put("level") { it.level.toString() }
                .put("caster") { it.name }
                .build()

        private val DEFAULT: (Actor, String) -> String? = { subject, key ->
            when {
                subject.metadata.containsKey(key) -> format(subject.metadata.getValue(key))
                subject.values.has(key) -> subject.values[key].total.toString()
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
    }
}