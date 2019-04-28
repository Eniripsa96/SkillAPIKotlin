package com.sucy.skill.dynamic.mechanic

import com.sucy.skill.api.skill.SkillProgress
import com.sucy.skill.api.skill.SkillShot
import com.sucy.skill.api.skill.TargetSkill
import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.math.formula.DynamicFormula

class CooldownMechanic : Mechanic() {
    override val key = "cooldown"

    private var skillName = "junk"
    private var percent = false
    private lateinit var seconds: DynamicFormula

    override fun initialize() {
        super.initialize()
        
        skillName = metadata.getString("skill", skillName)
        seconds = metadata.getFormula("seconds", -1.0)
    }

    override fun execute(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val change = compute(seconds, context.caster, target)
        if (skillName.equals("all", ignoreCase = true)) {
            recipient.skills.forEach { apply(it, change) }
        } else {
            val skill = recipient.skills[skillName] ?: recipient.skills[parentSkill.key]!!
            apply(skill, change)
        }
        return true
    }

    private fun apply(skill: SkillProgress, change: Double) {
        if (skill.level <= 0) return

        val cooldown = when {
            skill.data is SkillShot -> skill.data.cooldown.evaluate(skill.level.toDouble())
            skill.data is TargetSkill -> skill.data.cooldown.evaluate(skill.level.toDouble())
            else -> 0.0
        }

        if (percent) skill.cooldown.subtract(change * cooldown / 100.0)
        else skill.cooldown.subtract(change)
    }
}