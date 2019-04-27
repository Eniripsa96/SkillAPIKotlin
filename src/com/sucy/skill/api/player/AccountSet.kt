package com.sucy.skill.api.player

import java.util.*
import kotlin.collections.HashMap

/**
 * SkillAPIKotlin © 2018
 */
data class AccountSet internal constructor(val uuid: UUID) {
    val data = HashMap<Int, PlayerAccount>()

    private var active = -1

    val activeAccount: PlayerAccount?
            get() = data[active]
}