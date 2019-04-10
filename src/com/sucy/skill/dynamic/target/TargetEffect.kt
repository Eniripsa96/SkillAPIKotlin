package com.sucy.skill.dynamic.target

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor

abstract class TargetEffect : Effect() {
    override val type = EffectType.TARGET

    var alwaysCaster = false

    private var fromCaster = false
    private var exceptCaster = false
    private var random = true
    private var distinct = false
    private var max = 999

    override fun initialize() {
        fromCaster = metadata.getString("relative", "target").equals("caster", ignoreCase = true)
        max = metadata.getInt("max", 999)
        distinct = metadata.getBoolean("distinct", distinct)

        val resolveType = metadata.getString("resolve", "random")
        random = resolveType.equals("random", ignoreCase = true)

        val casterInclusion = metadata.getString("includeCaster", "default")
        alwaysCaster = casterInclusion.equals("always", ignoreCase = true)
        exceptCaster = alwaysCaster || casterInclusion.equals("never", ignoreCase = true)
    }

    override fun execute(context: CastContext, targets: List<Actor>): Boolean {
        val newTargets = if (fromCaster) {
            selectTargets(context, getTargets(context, context.caster))
        } else {
            selectTargets(context, targets.flatMap { getTargets(context, it) })
        }
        return newTargets.isNotEmpty() && executeChildren(context, newTargets)
    }

    fun selectTargets(context: CastContext, pool: Collection<Actor>): List<Actor> {
        val validTargets = pool.filterTo(ArrayList()) { isValidTarget(context, it) }
        if (alwaysCaster) validTargets.add(context.caster)

        return when {
            validTargets.size <= max -> validTargets
            random -> validTargets.shuffled().subList(0, max)
            else -> {
                val casterLoc = context.caster.location.coords
                validTargets.sortedBy { it.location.coords.dSq(casterLoc) }.subList(0, max)
            }
        }
    }

    fun isValidTarget(context: CastContext, target: Actor): Boolean {
        return (!exceptCaster || target != context.caster)
        // TODO - wall and ally checks
    }

    abstract fun getTargets(context: CastContext, target: Actor): List<Actor>
}