package com.sucy.skill

import com.sucy.skill.facade.api.event.EventBusProxy
import com.sucy.skill.util.io.ConfigHolder

/**
 * SkillAPIKotlin © 2018
 */
interface SkillAPIPlugin : ConfigHolder {
    val eventBusProxy: EventBusProxy<*>

    fun reload()
}
