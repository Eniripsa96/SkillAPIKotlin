package com.sucy.skill.util.text.context

import com.google.common.collect.ImmutableMap
import com.sucy.skill.api.skill.SkillProgress

class SkillFilterContext(
        val skill: SkillProgress
) : FilterContext<SkillProgress>("skill", skill, FILTERS, DEFAULT) {
    companion object {
        private val FILTERS = ImmutableMap.builder<String, (SkillProgress) -> String>()
                .put("name") { it.data.name }
                .put("lvl") { it.level.toString() }
                .put("level") { it.level.toString() }
                .put("max") { it.data.maxLevel.toString() }
                .build()

        private val DEFAULT: (SkillProgress, String) -> String? = { subject, key ->
            null
        }
    }
}