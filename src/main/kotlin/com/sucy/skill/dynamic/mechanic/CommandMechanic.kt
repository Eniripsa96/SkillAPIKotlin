package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

class CommandMechanic : Mechanic() {
    override val key = "command"

    private var command: String = "say Hello World!"

    override fun initialize() {
        super.initialize()
        command = metadata.getString("command", command)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        recipient.executeCommand(command)
        return true
    }
}