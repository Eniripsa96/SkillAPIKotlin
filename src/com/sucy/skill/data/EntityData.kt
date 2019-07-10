package com.sucy.skill.data

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.sucy.skill.api.player.AccountSet
import com.sucy.skill.api.skill.SkillSet
import com.sucy.skill.api.values.FlagSet
import com.sucy.skill.api.values.ValueSet
import java.util.*
import kotlin.collections.mutableMapOf

/**
 * SkillAPIKotlin © 2018
 */
class EntityData {
    val attributes = mutableMapOf<UUID, ValueSet>()
    val values = mutableMapOf<UUID, ValueSet>()
    val flags = mutableMapOf<UUID, FlagSet>()
    val metadata = mutableMapOf<UUID, MutableMap<String, Any>>()
    val accounts = mutableMapOf<UUID, AccountSet>()
    val skills = mutableMapOf<UUID, SkillSet>()
    val combatTimers = mutableMapOf<UUID, Long>()
    val playerIds: BiMap<UUID, String> = HashBiMap.create<UUID, String>()

    fun unload(uuid: UUID) {
        attributes.remove(uuid)
        values.remove(uuid)
        flags.remove(uuid)
        metadata.remove(uuid)
        accounts.remove(uuid)
        skills.remove(uuid)
    }
}