package com.sucy.skill.api.skill

import com.sucy.skill.facade.api.entity.Actor

class SkillSet {
    private val skills = HashMap<String, SkillProgress>()

    operator fun SkillSet.get(skillName: String): SkillProgress? {
        return skills[skillName]
    }

    fun giveSkill(owner: Actor, source: String, skill: Skill): SkillProgress {
        val entry = skills.getOrPut(skill.key) { SkillProgress(owner, skill) }
        entry.sources.add(source)
        return entry
    }

    fun removeSkills(source: String) {
        val keys = HashSet(skills.keys)
        keys.forEach {
            val skill = skills[it]
            skill.sources.remove(source)
            if (skill.sources.isEmpty()) {
                skills.remove(it)
            }
        }
    }
}