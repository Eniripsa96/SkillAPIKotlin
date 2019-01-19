package com.sucy.skill.api.skill

import com.sucy.skill.api.Icon
import com.sucy.skill.api.Levelable

open class Skill(name: String, icon: Icon, maxLevel: Int) : Levelable(name, icon, maxLevel)