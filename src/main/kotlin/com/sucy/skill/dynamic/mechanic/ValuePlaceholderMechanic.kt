package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.SkillAPI
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.common.ItemContext
import com.sucy.skill.facade.api.dependency.Dependency
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.facade.api.entity.Player

class ValuePlaceholderMechanic : Mechanic() {
    override val key = "value placeholder"

    private var valueKey = "placeholder"
    private var placeholder = "unspecified"

    override fun initialize() {
        super.initialize()
        valueKey = metadata.getString("key", valueKey)
        placeholder = metadata.getString("placeholder", placeholder)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        if (recipient !is Player) return false

        val placeholderApi = SkillAPI.getDependency(Dependency.PLACEHOLDER_API) ?: return false
        val result = placeholderApi.format(placeholder, recipient)
        val parsed: Any = if (NUMBER.matches(result)) result.toDouble() else result

        recipient.metadata[valueKey] = parsed
        return true
    }

    companion object {
        val NUMBER = Regex("[+-]?[0-9]+([.,][0-9]+)?")
    }
}