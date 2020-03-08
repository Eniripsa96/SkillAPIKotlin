package com.sucy.skill.util.text.context

import com.google.common.collect.ImmutableMap
import com.sucy.skill.api.profession.ProfessionProgress

class ProfessionFilterContext(
        subject: ProfessionProgress
) : FilterContext<ProfessionProgress>("class", subject, FILTERS, DEFAULT) {
    companion object {
        val FILTERS = ImmutableMap.builder<String, (ProfessionProgress) -> String>()
                .put("name") { it.data.name }
                .put("level") { it.level.toString() }
                .put("lvl") { it.level.toString() }
                .put("max") { it.data.maxLevel.toString() }
                .build()

        val DEFAULT: (ProfessionProgress, String) -> String? = { subject, key ->
            null
        }
    }
}