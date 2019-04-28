package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.player.AccountSet

/**
 * SkillAPIKotlin © 2018
 */
interface Player : Actor {
    val accounts: AccountSet
        get() = SkillAPI.entityData.accounts.getOrDefault(uuid, AccountSet.FAKE_ACCOUNT)
}