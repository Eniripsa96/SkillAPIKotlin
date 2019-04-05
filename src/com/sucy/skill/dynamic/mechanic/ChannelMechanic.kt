package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.values.Status
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class ChannelMechanic : Effect() {
    override val key = "channel"
    override val type = EffectType.MECHANIC

    private var stationary = false
    private var breakOnDamage = false
    private var breakOnSilence = true
    private lateinit var duration: DynamicFormula

    override fun initialize() {
        stationary = metadata.getBoolean("stationary", stationary)
        duration = metadata.getFormula("duration", 2.0)
        breakOnDamage = metadata.getBoolean("breakOnDamage", breakOnDamage)
        breakOnSilence = metadata.getBoolean("breakOnSilence", breakOnSilence)
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        if (context.caster.flags.isActive(Status.CHANNEL.flag)) return false

        val caster = context.caster
        compute(duration, caster, caster)
        caster.metadata[CHANNEL_META] = { executeChildren(context, targets) }
        caster.flags.set(Status.CHANNEL.flag)
        caster.metadata[CHANNEL_DAMAGE_BREAK] = breakOnDamage
        caster.metadata[CHANNEL_SILENCE_BREAK] = breakOnSilence
        return true
    }

    companion object {
        const val CHANNEL_META = "api_channel_effect"
        const val CHANNEL_DAMAGE_BREAK = "api_channel_damage"
        const val CHANNEL_SILENCE_BREAK = "api_channel_silence"
    }
}
