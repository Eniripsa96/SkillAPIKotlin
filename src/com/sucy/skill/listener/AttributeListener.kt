package com.sucy.skill.listener

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.attribute.AttributeDefinitions
import com.sucy.skill.api.attribute.Stat
import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.data.loader.impl.attribute.AttributeDefinitionsDataLoader
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.actor.ActorDamagedByActorEvent
import com.sucy.skill.facade.api.event.actor.DamageSource
import com.sucy.skill.util.io.Config

class AttributeListener : SkillAPIListener {

    private lateinit var definitions: AttributeDefinitions

    override fun init() {
        val config = Config(SkillAPI.plugin, "attributes")
        definitions = AttributeDefinitionsDataLoader.transformAndLoad("attributes", config.data)
    }

    override fun cleanup() {
        SkillAPI.server.players.onlinePlayers.forEach { cleanup(it) }
    }

    private fun cleanup(player: Player) {

    }

    @Listen(step = Step.LATE, ignoreCancelled = true)
    fun onDamage(event: ActorDamagedByActorEvent) {
        modifyDamage(event, event.attacker, Stat.PHYSICAL_DAMAGE, Stat.SKILL_DAMAGE)
        modifyDamage(event, event.actor, Stat.PHYSICAL_DEFENSE, Stat.SKILL_DEFENSE)
    }

    private fun modifyDamage(event: ActorDamagedByActorEvent, actor: Actor, physicalStat: Stat, skillStat: Stat) {
        val stats = ArrayList<String>()
        when {
            event.damageType.equals(PHYSICAL, ignoreCase = true) -> {
                stats.add(physicalStat.key)
            }
            event.source == DamageSource.SKILL -> {
                stats.add(skillStat.key + "-" + event.damageType)
                stats.add(skillStat.key)
            }
        }

        var newDamage = event.amount
        stats.forEach { newDamage = definitions.forStat(it).apply(actor, newDamage) }
        event.amount = newDamage
    }

    companion object {
        const val PHYSICAL = "physical"
    }
}