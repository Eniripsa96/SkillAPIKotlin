package com.sucy.skill.facade.api.event.player

import com.sucy.skill.api.event.Event
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
data class AsyncPlayerLoginEvent(
        val uuid: UUID
) : Event