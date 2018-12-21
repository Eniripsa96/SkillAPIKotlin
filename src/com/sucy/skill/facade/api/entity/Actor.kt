package com.sucy.skill.facade.api.entity

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.attribute.AttributeSet
import com.sucy.skill.api.attribute.FlagSet
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
interface Actor : Entity {
    val uuid: UUID

    val attributes: AttributeSet
        get() {
            return SkillAPI.entityData.attributes.computeIfAbsent(uuid) { AttributeSet() }
        }

    val flags: FlagSet
        get() {
            return SkillAPI.entityData.flags.computeIfAbsent(uuid) { FlagSet() }
        }

    fun hasPermission(permission: String): Boolean
}

