package com.sucy.skill.dynamic

import com.google.common.collect.ImmutableMap
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.api.skill.SkillProgress
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

    lateinit var parentSkill: Skill

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
            worked = (passed && counts) || worked
        }
        return worked
    }

    fun cleanUp(caster: Actor) {
        doCleanUp(caster)
        children.forEach { it.cleanUp(caster) }
    }

    fun compute(formula: DynamicFormula, caster: Actor, target: Actor): Double {
        formulaCaster = caster
        formulaTarget = target
        return formula.evaluate(formulaGetter)
    }

    fun compute(key: String, caster: Actor, target: Actor): Double {
        return compute(metadata.getFormula(key), caster, target)
    }

    /**
     * Called when a player logs off, dies, or unlearns a skill
     */
    open fun doCleanUp(caster: Actor) { }

    companion object {
        private var formulaCaster: Actor? = null
        private var formulaTarget: Actor? = null

        private val formulaGetter: (String) -> Double = {
            if (it.startsWith("target.")) getValue(formulaTarget!!, it.substring(7))
            else getValue(formulaCaster!!, it)
        }

        fun getValue(actor: Actor, key: String): Double {
            val common = VALUE_KEYS[key]
            return common?.invoke(actor) ?: actor.values[key].total
        }

        private val VALUE_KEYS = ImmutableMap.builder<String, (Actor) -> Double>()
                .put("health") { it.health }
                .put("maxhealth") { it.maxHealth }
                .put("lvl") { it.level.toDouble() }
                .put("level") { it.level.toDouble() }
                .build()
    }
}