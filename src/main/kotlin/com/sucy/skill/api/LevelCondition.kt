package com.sucy.skill.api

import com.sucy.skill.facade.api.entity.Actor

/**
 * SkillAPIKotlin © 2018
 */
interface LevelCondition {
    fun canLevelUp(target: Levelable, owner: Actor): Boolean
}
