package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.values.FlagSet
import com.sucy.skill.api.values.ValueSet
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface Actor : Entity {
    val uuid: UUID
    val health: Double
    val maxHealth: Double
    val level: Int

    val attributes: ValueSet
        get() {
            return SkillAPI.entityData.attributes.computeIfAbsent(uuid) { ValueSet() }
        }

    val values: ValueSet
        get() {
            return SkillAPI.entityData.values.computeIfAbsent(uuid) { ValueSet() }
        }

    val flags: FlagSet
        get() {
            return SkillAPI.entityData.flags.computeIfAbsent(uuid) { FlagSet() }
        }

    fun hasPermission(permission: String): Boolean
}

