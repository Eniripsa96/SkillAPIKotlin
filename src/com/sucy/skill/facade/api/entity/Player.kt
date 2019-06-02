package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.player.AccountSet
import com.sucy.skill.api.player.PlayerAccount
import com.sucy.skill.facade.api.data.GameMode
import com.sucy.skill.facade.api.event.player.ManaCost
import com.sucy.skill.facade.api.event.player.ManaSource
import com.sucy.skill.facade.api.event.player.PlayerManaGainEvent
import com.sucy.skill.facade.api.event.player.PlayerManaLossEvent
import com.sucy.skill.util.math.limit

/**
 * SkillAPIKotlin © 2018
 */
interface Player : Actor {
    var gameMode: GameMode
    var enchantingLevel: Int
    var enchantingExp: Double

    override val mana: Double
        get() = accounts.activeAccount.mana
    override var maxMana: Double
        get() = accounts.activeAccount.maxMana
        set(value) {
            accounts.activeAccount.maxMana = value
        }

    val accounts: AccountSet
        get() = SkillAPI.entityData.accounts.getOrDefault(uuid, AccountSet.fake())
    val activeAccount: PlayerAccount
        get() = accounts.activeAccount

    override fun giveMana(amount: Double, reason: ManaSource): Boolean {
        if (amount < 0 || mana >= maxMana) return false

        val event = PlayerManaGainEvent(this, amount, reason)
        val modified = SkillAPI.eventBus.trigger(event)
        if (!modified.cancelled && modified.amount > 0) {
            val newMana = mana + modified.amount
            accounts.activeAccount.mana = limit(newMana, 0.0, maxMana)
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
            accounts.activeAccount.mana = limit(newMana, 0.0, maxMana)
            return true
        }
        return false
    }
}