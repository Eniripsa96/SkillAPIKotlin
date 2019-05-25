package com.sucy.skill.data

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.sucy.skill.api.player.AccountSet
import com.sucy.skill.api.skill.SkillSet
import com.sucy.skill.api.values.FlagSet
import com.sucy.skill.api.values.ValueSet
import java.util.*
import kotlin.collections.HashMap

/**
 * SkillAPIKotlin © 2018
 */
class EntityData {
    val attributes = HashMap<UUID, ValueSet>()
    val values = HashMap<UUID, ValueSet>()
    val flags = HashMap<UUID, FlagSet>()
    val metadata = HashMap<UUID, MutableMap<String, Any>>()
    val accounts = HashMap<UUID, AccountSet>()
    val skills = HashMap<UUID, SkillSet>()
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