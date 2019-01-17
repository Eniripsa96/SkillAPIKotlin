package com.sucy.skill.config.category

import com.google.common.collect.ImmutableMap
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class AccountSettings(config: Data) {
    val mainGroup = config.getString("main-class-group", "default")
    val onePerClass = config.getBoolean("one-per-class", false)
    val maxAccounts = config.getInt("max-accounts", 3)
    val extraAccounts: Map<String, Int>

    init {
        val extraAccountsBuilder = ImmutableMap.builder<String, Int>()
        val extraAccountsConfig = config.getOrCreateSection("perm-accounts")
        extraAccountsConfig.keys().forEach { extraAccountsBuilder.put(it, extraAccountsConfig.getInt(it)) }
        extraAccounts = extraAccountsBuilder.build()
    }
}