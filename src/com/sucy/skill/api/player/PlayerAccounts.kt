package com.sucy.skill.api.player

import java.util.*
import kotlin.collections.HashMap

/**
 * SkillAPIKotlin © 2018
 */
data class PlayerAccounts internal constructor(val uuid: UUID) {
    val data = HashMap<Int, PlayerData>()

    private var active = -1

    val activeAccount: PlayerData?
            get() = data[active]
}