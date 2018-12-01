package com.sucy.skill.config.category

import com.google.common.collect.ImmutableMap
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class AccountSettings(config: Data) {
    val mainGroup = config.getString("main-class-group")
    val onePerClass = config.getBoolean("one-per-class")
    val maxAccounts = config.getBoolean("max-accounts")
    val extraAccounts: Map<String, Int>

    init {
        val extraAccountsBuilder = ImmutableMap.builder<String, Int>()
        val extraAccountsConfig = config.getOrCreateSection("perm-accounts")
        extraAccountsConfig.keys().forEach { extraAccountsBuilder.put(it, extraAccountsConfig.getInt(it)) }
        extraAccounts = extraAccountsBuilder.build()
    }
}