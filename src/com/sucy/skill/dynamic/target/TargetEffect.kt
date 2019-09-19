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
            selectTargets(context, targets.flatMap { getTargets(context.caster, context, it) })
        } else {
            selectTargets(context, targets.flatMap { getTargets(it, context, it) })
        }
        return newTargets.isNotEmpty() && executeChildren(context, newTargets)
    }

    fun selectTargets(context: CastContext, pool: Collection<Actor>): List<Actor> {
        val byId = pool.groupBy { it.uuid }.filter { isValidTarget(context, it.value.first()) }
        val reduced = if (distinct) byId.map { it.value.first() } else byId.flatMap { it.value }
        val capped = when {
            reduced.size <= max -> reduced
            random -> reduced.shuffled().subList(0, max)
            else -> {
                val casterLoc = context.caster.location.coords
                reduced.sortedBy { it.location.coords.dSq(casterLoc) }.subList(0, max)
            }
        }

        return if (alwaysCaster) capped + context.caster else capped
    }

    fun isValidTarget(context: CastContext, target: Actor): Boolean {
        return (!exceptCaster || target != context.caster)
        // TODO - wall and ally checks
    }

    abstract fun getTargets(from: Actor, context: CastContext, target: Actor): List<Actor>
}