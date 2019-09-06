package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class PermissionCondition : Condition() {
    override val key = "permission"

    private var permission = "skillapi.basic"

    override fun initialize() {
        super.initialize()
        permission = metadata.getString("permission", permission)
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        return recipient.hasPermission(permission)
    }
}