package com.sucy.skill.util.text.context

import com.google.common.collect.ImmutableMap
import com.sucy.skill.api.player.PlayerAccount
import com.sucy.skill.api.profession.ProfessionProgress
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Entity
import com.sucy.skill.facade.api.entity.Player
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

                // Player-only filters
                .put("class") { getProfession(it)?.data?.name ?: "" }
                .put("attr") { getAccount(it)?.attributePoints?.toString() ?: "0" }
                .put("exp") { getProfession(it)?.exp?.toInt()?.toString() ?: "0" }
                .put("expReq") { getProfession(it)?.requiredExp?.toInt()?.toString() ?: "0" }
                .put("expLeft") {
                    val profession = getProfession(it) ?: return@put "0"
                    ceil(profession.requiredExp - profession.exp).toInt().toString()
                }

                .build()

        private fun getAccount(actor: Actor): PlayerAccount? {
            return if (actor is Player) {
                actor.activeAccount
            } else {
                null
            }
        }

        private fun getProfession(actor: Actor): ProfessionProgress? {
            return if (actor is Player) {
                actor.activeAccount.professionSet.main
            } else {
                null
            }
        }

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