package com.sucy.skill

import com.sucy.skill.facade.api.Scheduler
import com.sucy.skill.facade.api.Server
import com.sucy.skill.facade.api.dependency.Dependencies
import com.sucy.skill.facade.api.dependency.Dependency
import com.sucy.skill.facade.api.event.EventBusProxy
import com.sucy.skill.util.io.ConfigHolder

/**
 * SkillAPIKotlin © 2018
 */
interface SkillAPIPlatform : ConfigHolder {
    val eventBusProxy: EventBusProxy<*>
    val scheduler: Scheduler
    val server: Server
    val dependencies: Dependencies

    fun reload()
}
