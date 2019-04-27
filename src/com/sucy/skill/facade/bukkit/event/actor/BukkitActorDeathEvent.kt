package com.sucy.skill.facade.bukkit.event.actor

import com.sucy.skill.facade.api.data.Item
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.event.actor.ActorDeathEvent
import com.sucy.skill.facade.bukkit.BukkitUtil
import com.sucy.skill.facade.bukkit.data.BukkitItem
import com.sucy.skill.facade.bukkit.skillAPI
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDeathEvent

data class BukkitActorDeathEvent(
        override val actor: Actor,
        override val killer: Actor?,
        override val drops: List<Item>,
        override val exp: Int
) : ActorDeathEvent {

    constructor(event: EntityDeathEvent): this(
            actor = event.entity.skillAPI() as Actor,
            killer = event.entity.killer?.skillAPI() as Actor?,
            drops = event.drops.map { BukkitItem(it) },
            exp = event.droppedExp
    )

    fun restore(): EntityDeathEvent {
        return EntityDeathEvent(
                BukkitUtil.toBukkit(actor) as LivingEntity,
                drops.map {  }
                )
    }
}