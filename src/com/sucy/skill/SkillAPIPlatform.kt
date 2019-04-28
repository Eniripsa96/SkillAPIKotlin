package com.sucy.skill

import com.sucy.skill.facade.api.Scheduler
import com.sucy.skill.facade.api.event.EventBusProxy
import com.sucy.skill.util.io.ConfigHolder

/**
 * SkillAPIKotlin © 2018
 */
interface SkillAPIPlatform : ConfigHolder {
    val eventBusProxy: EventBusProxy<*>
    val scheduler: Scheduler

    fun reload()
}
