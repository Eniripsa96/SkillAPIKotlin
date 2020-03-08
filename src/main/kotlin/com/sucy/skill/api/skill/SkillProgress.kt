package com.sucy.skill.api.skill

import com.sucy.skill.api.LevelProgress
import com.sucy.skill.api.values.Cooldown
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.text.context.FilterContext
import com.sucy.skill.util.text.context.SkillFilterContext

class SkillProgress(data: Skill) : LevelProgress<Skill>(data) {
    val sources = HashSet<String>()
    var cooldown = Cooldown()
    val isUnlocked: Boolean
        get() = level > 0

    override val filterContext: FilterContext<*>
        get() = SkillFilterContext(this)
}