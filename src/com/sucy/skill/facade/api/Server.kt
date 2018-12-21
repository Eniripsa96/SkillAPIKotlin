package com.sucy.skill.facade.api

import com.sucy.skill.facade.api.managers.PlayerManager

/**
 * SkillAPIKotlin © 2018
 */
interface Server {
    val players: PlayerManager
}