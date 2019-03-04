package com.sucy.skill.facade.api

import com.sucy.skill.facade.api.managers.PlayerManager
import com.sucy.skill.facade.api.managers.TaskManager

/**
 * SkillAPIKotlin © 2018
 */
interface Server {
    val players: PlayerManager
    val taskManager: TaskManager

    fun getWorld(name: String): World
}