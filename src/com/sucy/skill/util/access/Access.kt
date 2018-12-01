package com.sucy.skill.util.access

import com.google.common.collect.ImmutableMap
import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
class Access(
        private val accessors: Map<String, (context: Actor?) -> Any?>,
        private val subGroups: Map<String, Access> = ImmutableMap.of()) {

    fun find(key: String, context: Actor? = null): Any? {
        // Navigate to the correct filter
        var access = this
        val keys = key.split(':')
        for (i in 0 until keys.size) {
            access = subGroups.getOrDefault(keys[i], access)
        }

        // Apply the filter if it has a value for the key
        return access.accessors[keys.last()]?.invoke(context)
    }
}