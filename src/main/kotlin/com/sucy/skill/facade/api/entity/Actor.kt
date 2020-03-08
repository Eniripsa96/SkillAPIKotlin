package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.skill.*
import com.sucy.skill.api.values.FlagSet
import com.sucy.skill.api.values.ValueSet
import com.sucy.skill.command.CommandSender
import com.sucy.skill.config.category.ActorSizes
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.data.effect.PotionEffect
import com.sucy.skill.facade.api.data.effect.PotionType
import com.sucy.skill.facade.api.data.inventory.ActorInventory
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.api.event.actor.DamageSource
import com.sucy.skill.facade.api.event.player.ManaCost
import com.sucy.skill.facade.api.event.player.ManaSource
import com.sucy.skill.util.math.Targeting
import com.sucy.skill.util.math.Vector3
import com.sucy.skill.util.math.limit
import com.sucy.skill.util.math.sq
import java.util.*
import kotlin.collections.mutableMapOf
import kotlin.math.min

/**
 * SkillAPIKotlin © 2018
 */
interface Actor : Entity, CommandSender {
    val uuid: UUID
    var health: Double
    var maxHealth: Double
    var mana: Double
        get() = 0.0
        set(_) {}
    var maxMana: Double
        get() = 0.0
        set(_) {}
    var food: Double
    var saturation: Double
    val level: Int
        get() = 0
    val dead: Boolean
    val exists: Boolean
    val inventory: ActorInventory
    val potionEffects: List<PotionEffect>

    val attributes: ValueSet
        get() = SkillAPI.entityData.attributes.computeIfAbsent(uuid) { ValueSet() }

    val values: ValueSet
        get() = SkillAPI.entityData.values.computeIfAbsent(uuid) { ValueSet() }

    val flags: FlagSet
        get() = SkillAPI.entityData.flags.computeIfAbsent(uuid) { FlagSet() }

    val metadata: MutableMap<String, Any>
        get() = SkillAPI.entityData.metadata.computeIfAbsent(uuid) { mutableMapOf() }

    val skills: SkillSet
        get() = SkillAPI.entityData.skills.computeIfAbsent(uuid) { SkillSet() }

    val size: ActorSizes.Size
        get() = SkillAPI.settings.sizes.sizeOf(type)

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

    fun cast(skill: SkillProgress): Boolean {
        return cast(skill.data, skill.level)
    }

    fun cast(skill: Skill, level: Int): Boolean {
        return when (skill) {
            is SkillShot -> skill.cast(this, level)
            is TargetSkill -> {
                val target = Targeting.getClosestLineOfSightTarget(location, 5.0) ?: return false
                val isAlly = false // TODO - add ally detection
                skill.cast(this, target, level, isAlly)
            }
            else -> false
        }
    }

    fun markInCombat() {
        SkillAPI.entityData.combatTimers[uuid] = System.currentTimeMillis()
    }

    fun isInCombat(seconds: Double): Boolean {
        val lastMark = SkillAPI.entityData.combatTimers[uuid] ?: return false
        val elapsed = System.currentTimeMillis() - lastMark
        return elapsed < seconds * 1000
    }

    fun dSq(pos: Vector3): Double {
        val (x, y, z) = location.coords
        val (i, j, k) = pos
        val (diameter, height) = this.size
        val rad = diameter / 2

        val a = limit(i, x - rad, x + rad)
        val b = limit(j, y, y + height)
        val c = limit(k, z - rad, z + rad)

        return sq(a - i) + sq(b - j) + sq(c - k)
    }

    fun playSound(from: Location, sound: String, volume: Float, pitch: Float) {}
    fun giveMana(amount: Double, reason: ManaSource): Boolean = false
    fun takeMana(amount: Double, reason: ManaCost): Boolean = false
    fun applyPotionEffect(effect: PotionEffect)
    fun removePotionEffect(type: PotionType)
}

