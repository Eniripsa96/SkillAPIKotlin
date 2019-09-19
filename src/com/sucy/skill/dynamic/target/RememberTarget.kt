package com.sucy.skill.dynamic.target

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor

class RememberTarget : TargetEffect() {
    override val key = "remember"

    private var metadataKey = "target"

    override fun initialize() {
        super.initialize()

        metadataKey = metadata.getString("key", metadataKey)
    }

    override fun getTargets(from: Actor, context: CastContext, target: Actor): List<Actor> {
        val data = from.metadata[metadataKey]
        return when(data) {
            is List<*> -> if (data.all { it is Actor }) data as List<Actor> else emptyList()
            is Actor -> listOf(data)
            else -> emptyList()
        }
    }
}