package com.sucy.skill.api.profession

import com.sucy.skill.api.LevelProgress
import com.sucy.skill.facade.api.entity.Actor

class ProfessionProgress(owner: Actor, data: Profession) : LevelProgress<Profession>(owner, data) {
    
}