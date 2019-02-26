package com.sucy.skill.data

import com.sucy.skill.api.values.FlagSet
import com.sucy.skill.api.values.ValueSet
import com.sucy.skill.api.player.PlayerAccounts
import java.util.*
import kotlin.collections.HashMap

/**
 * SkillAPIKotlin © 2018
 */
class EntityData {
    val attributes = HashMap<UUID, ValueSet>()
    val values = HashMap<UUID, ValueSet>()
    val flags = HashMap<UUID, FlagSet>()
    val accounts = HashMap<UUID, PlayerAccounts>()

    fun unload(uuid: UUID) {
        attributes.remove(uuid)
        values.remove(uuid)
        flags.remove(uuid)
        accounts.remove(uuid)
    }
}