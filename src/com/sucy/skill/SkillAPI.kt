package com.sucy.skill

import com.sucy.skill.api.event.EventBus
import com.sucy.skill.config.Settings
import com.sucy.skill.data.EntityData

/**
 * SkillAPIKotlin © 2018
 */
object SkillAPI {
    lateinit var plugin: SkillAPIPlugin
        private set
    lateinit var settings: Settings
        private set
    lateinit var eventBus: EventBus
        private set
    lateinit var entityData: EntityData
        private set

    fun init(plugin: SkillAPIPlugin) {
        this.plugin = plugin
        this.settings = Settings(plugin)
        this.eventBus = EventBus(plugin.eventBusProxy)
        this.entityData = EntityData()
    }
}