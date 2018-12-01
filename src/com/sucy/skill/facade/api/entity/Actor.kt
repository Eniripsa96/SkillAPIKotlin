package com.sucy.skill.facade.api.entity

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface Actor : Entity {
    val uuid: UUID
    fun hasPermission(permission: String): Boolean
}
