package com.sucy.skill.dynamic.trigger

import com.sucy.skill.api.event.Event
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.dynamic.DynamicSkill
import com.sucy.skill.util.io.Data

/**
 * Possible triggers for dynamic skill effects
 */
interface Trigger<E : Event> {

    /**
     * @return unique key for the trigger
     */
    val key: String

    /**
     * @return class of the event related to the trigger
     */
    val event: Class<E>

    /**
     * @param event event details
     * @param level the level of the owning skill
     * @param settings skill settings
     * @return true if the skill should activate, false otherwise
     */
    fun shouldTrigger(event: E, level: Int, settings: Data): Boolean

    /**
     * Reads data from the event and provides values to the caster's value data. This can be used within
     * skills for more flexible effects. An example of this in base triggers is the Launch trigger providing
     * the speed a projectile was launched so mechanics can replace it with equally-fast projectiles.
     *
     * @param event event details
     * @param data caster's value data to populate
     */
    fun setValues(event: E, data: MutableMap<String, Any>)

    /**
     * Fetches the caster as determined by the triggering event.
     *
     * @param event event details
     * @return the one to apply the trigger for
     */
    fun getCaster(event: E): Actor?

    /**
     * Fetches the target as determined by the triggering event. This can be the same as the caster.
     *
     * @param event event details
     * @param settings skill settings
     * @return the one being affected by the trigger (initial target)
     */
    fun getTarget(event: E, settings: Data): Actor?

    /**
     * Handles applying other effects after the skill resolves
     *
     * @param event event details
     * @param skill skill to resolve
     */
    fun postProcess(event: E, skill: DynamicSkill) {}
}
