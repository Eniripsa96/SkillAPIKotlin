package com.sucy.skill.dynamic.fakes

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.World
import com.sucy.skill.facade.api.data.Location
import com.sucy.skill.facade.api.data.effect.PotionEffect
import com.sucy.skill.facade.api.data.effect.PotionType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.EntityType
import com.sucy.skill.util.math.Vector3
import java.util.*

class FakeActor(override var location: Location) : Actor {
    override val uuid = ID
    override var health = 0.0
    override var maxHealth = 0.0
    override var food = 0.0
    override var saturation = 0.0
    override val dead = false
    override val exists = true
    override val inventory = FakeInventory
    override val potionEffects: List<PotionEffect> = emptyList()
    override fun executeCommand(command: String) {}
    override fun applyPotionEffect(effect: PotionEffect) {}
    override fun removePotionEffect(type: PotionType) {}
    override var velocity: Vector3 = Vector3(0.0, 0.0, 0.0)
    override val type: EntityType = TYPE
    override var name: String = NAME
    override val world: World = SkillAPI.server.getWorld(location.world)
    override var fireTicks: Long = 0L
    override fun isOnGround(): Boolean = false
    override fun sendMessage(message: String) {}
    override fun hasPermission(permission: String): Boolean = true

    companion object {
        private const val NAME = "Fake Entity"
        private val TYPE = EntityType.of("FAKE")
        private val ID = UUID.fromString("00000000-0000-0000-0000-000000000000")
    }
}