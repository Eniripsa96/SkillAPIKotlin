package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.skill.SkillSet
import com.sucy.skill.api.values.FlagSet
import com.sucy.skill.api.values.ValueSet
import com.sucy.skill.command.CommandSender
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

/**
 * SkillAPIKotlin © 2018
 */
interface Actor : Entity, CommandSender {
    val uuid: UUID
    var health: Double
    val maxHealth: Double
    val level: Int
    val dead: Boolean
    val exists: Boolean

    val attributes: ValueSet
        get() = SkillAPI.entityData.attributes.computeIfAbsent(uuid) { ValueSet() }

    val values: ValueSet
        get() = SkillAPI.entityData.values.computeIfAbsent(uuid) { ValueSet() }

    val flags: FlagSet
        get() = SkillAPI.entityData.flags.computeIfAbsent(uuid) { FlagSet() }

    val metadata: MutableMap<String, Any>
        get() = SkillAPI.entityData.metadata.computeIfAbsent(uuid) { HashMap() }

    val skills: SkillSet
        get() = SkillAPI.entityData.skills.computeIfAbsent(uuid) { SkillSet() }

    fun hasPermission(permission: String): Boolean
    fun executeCommand(command: String)

    fun heal(amount: Double) {
        health = min(maxHealth, health + amount)
    }
}

