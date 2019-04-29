package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.player.AccountSet
import com.sucy.skill.facade.api.event.player.ManaCost
import com.sucy.skill.facade.api.event.player.ManaSource
import com.sucy.skill.facade.api.event.player.PlayerManaGainEvent
import com.sucy.skill.facade.api.event.player.PlayerManaLossEvent
import com.sucy.skill.util.math.limit

/**
 * SkillAPIKotlin © 2018
 */
interface Player : Actor {
    override val mana: Double
        get() = accounts.activeAccount?.mana ?: 0.0
    override var maxMana: Double
        get() = accounts.activeAccount?.maxMana ?: 0.0
        set(value) {
            accounts.activeAccount?.let { it.maxMana = value }
        }

    val accounts: AccountSet
        get() = SkillAPI.entityData.accounts.getOrDefault(uuid, AccountSet.FAKE_ACCOUNT)

    override fun giveMana(amount: Double, reason: ManaSource): Boolean {
        if (amount < 0 || mana >= maxMana) return false

        val event = PlayerManaGainEvent(this, amount, reason)
        val modified = SkillAPI.eventBus.trigger(event)
        if (!modified.cancelled && modified.amount > 0) {
            val newMana = mana + modified.amount
            accounts.activeAccount?.let { it.mana = limit(newMana, 0.0, maxMana) }
            return true
        }
        return false
    }

    override fun takeMana(amount: Double, reason: ManaCost): Boolean {
        if (amount < 0 || mana <= 0) return false

        val event = PlayerManaLossEvent(this, amount, reason)
        val modified = SkillAPI.eventBus.trigger(event)
        if (!modified.cancelled && modified.amount > 0) {
            val newMana = mana - modified.amount
            accounts.activeAccount?.let { it.mana = limit(newMana, 0.0, maxMana) }
            return true
        }
        return false
    }
}