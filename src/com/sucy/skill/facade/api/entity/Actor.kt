package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.skill.SkillSet
import com.sucy.skill.api.values.FlagSet
import com.sucy.skill.api.values.ValueSet
import com.sucy.skill.command.CommandSender
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.api.event.actor.DamageSource
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.min

/**
 * SkillAPIKotlin © 2018
 */
interface Actor : Entity, CommandSender {
    val uuid: UUID
    var health: Double
    var maxHealth: Double
    var food: Double
    var saturation: Double
    val level: Int
    val dead: Boolean
    val exists: Boolean
    val inventory: ActorInventory

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

    fun damage(
            amount: Double,
            attacker: Actor,
            source: DamageSource,
            type: String,
            trueDamage: Boolean
    ) {
        val event = ActorDamagedByActorEvent(
                actor = this,
                source = source,
                damageType = type,
                attacker = attacker,
                amount = amount
        )

        val modified = SkillAPI.eventBus.trigger(event)
        if (modified.cancelled || modified.amount <= 0) return

        health -= modified.amount
    }

    fun heal(amount: Double) {
        health = min(maxHealth, health + amount)
    }

    fun playSound(from: Location, sound: String, volume: Float, pitch: Float) {}
}

