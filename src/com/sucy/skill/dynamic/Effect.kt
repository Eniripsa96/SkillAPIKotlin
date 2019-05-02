package com.sucy.skill.dynamic

import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.math.formula.DynamicFormula

/**
 * SkillAPIKotlin © 2018
 */
abstract class Effect {
    abstract val key: String
    abstract val type: EffectType

    private val children = ArrayList<Effect>()
    protected val metadata = Data()

    lateinit var parentSkill: DynamicSkill

    /**
     * Called after all data is loaded to parse any settings from the metadata
     */
    abstract fun initialize()

    /**
     * Called every time the skill is used
     */
    abstract fun execute(context: CastContext, targets: List<Actor>): Boolean

    fun executeChildren(context: CastContext, targets: List<Actor>): Boolean {
        if (targets.isEmpty()) return false

        var worked = false
        children.forEach {
            val counts = !it.metadata.getString("counts", "true").equals("false", true)
            val passed = it.execute(context, targets)
            context.last = passed
            worked = (passed && counts) || worked
        }
        return worked
    }

    fun cleanUp(caster: Actor) {
        doCleanUp(caster)
        children.forEach { it.cleanUp(caster) }
    }

    fun compute(formula: DynamicFormula, caster: Actor, target: Actor): Double {
        return formula.evaluate(caster, target)
    }

    fun compute(key: String, caster: Actor, target: Actor): Double {
        return compute(metadata.getFormula(key), caster, target)
    }

    /**
     * Called when a player logs off, dies, or unlearns a skill
     */
    open fun doCleanUp(caster: Actor) { }
}