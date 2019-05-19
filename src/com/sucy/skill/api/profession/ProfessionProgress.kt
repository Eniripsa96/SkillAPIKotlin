package com.sucy.skill.api.profession

import com.sucy.skill.api.LevelProgress
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.text.context.FilterContext
import com.sucy.skill.util.text.context.ProfessionFilterContext

class ProfessionProgress(data: Profession) : LevelProgress<Profession>(data) {
    var history: List<Profession> = emptyList()

    override val filterContext: FilterContext<*>
        get() = ProfessionFilterContext(this)
}