package com.sucy.skill.api.skill

import com.sucy.skill.api.LevelProgress
import com.sucy.skill.api.values.Cooldown
import com.sucy.skill.facade.api.entity.Actor

class SkillProgress(owner: Actor, data: Skill) : LevelProgress<Skill>(owner, data) {
    val sources = HashSet<String>()
    var cooldown = Cooldown()
}