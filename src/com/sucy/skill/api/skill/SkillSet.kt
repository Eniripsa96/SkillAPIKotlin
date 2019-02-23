package com.sucy.skill.api.skill

import com.sucy.skill.facade.api.entity.Actor

class SkillSet {
    private val skills = HashMap<String, SkillProgress>()

    operator fun SkillSet.get(skillName: String): SkillProgress? {
        return skills[skillName]
    }

    /**
     * Adds a [skill] to the available set of skills for the [owner]. The [source]
     * should be a unique identifier telling where the skill is coming from. Returns
     * the [SkillProgress] instance for the skill.
     */
    fun giveSkill(owner: Actor, source: String, skill: Skill): SkillProgress {
        val entry = skills.getOrPut(skill.key) { SkillProgress(owner, skill) }
        entry.sources.add(source)
        return entry
    }

    /**
     * Removes all skills provided through the [source]. Skills will not be removed
     * if they either weren't given by the [source] or were also provided by a different source.
     */
    fun removeSkills(source: String) {
        val keys = HashSet(skills.keys)
        keys.forEach {
            val skill = skills.getValue(it)
            skill.sources.remove(source)
            if (skill.sources.isEmpty()) {
                skills.remove(it)
            }
        }
    }
}