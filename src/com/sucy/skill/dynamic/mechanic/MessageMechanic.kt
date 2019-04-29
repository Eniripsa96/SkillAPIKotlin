package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.text.DynamicFilter

class MessageMechanic : Mechanic() {
    override val key = "message"

    private var message = "Hello World!"

    override fun initialize() {
        super.initialize()

        message = metadata.getString("message", message)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val message = DynamicFilter.apply(this.message, context.caster, target)
        recipient.sendMessage(message)
        return true
    }
}