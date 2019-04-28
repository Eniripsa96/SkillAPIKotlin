package com.sucy.skill.api.skill

class SkillSet {
    private val byName = HashMap<String, SkillProgress>()
    private val byKey = HashMap<String, SkillProgress>()

    operator fun get(skillKey: String): SkillProgress? {
        val lower = skillKey.toLowerCase()
        return byName[lower] ?: byKey[lower]
    }

    fun add(progress: SkillProgress) {
        byName[progress.data.name.toLowerCase()] = progress
        byKey[progress.data.key] = progress
    }

    /**
     * Adds a [skill] to the available set of skills. The [source]
     * should be a unique identifier telling where the skill is coming from. Returns
     * the [SkillProgress] instance for the skill.
     */
    fun giveSkill(source: String, skill: Skill): SkillProgress {
        val entry = byName.getOrPut(skill.name.toLowerCase()) { SkillProgress(skill) }
        byKey[skill.key] = entry
        entry.sources.add(source)
        return entry
    }

    /**
     * Removes all skills provided through the [source]. Skills will not be removed
     * if they either weren't given by the [source] or were also provided by a different source.
     */
    fun removeSkills(source: String) {
        byName.entries.removeIf {
            it.value.sources.remove(source)
            it.value.sources.isEmpty()
        }
        byKey.entries.removeIf {
            it.value.sources.remove(source)
            it.value.sources.isEmpty()
        }
    }

    /**
     * Iterates through the set of skills, applying the [handler] to each.
     */
    fun forEach(handler: (SkillProgress) -> Unit) {
        byName.values.forEach(handler)
    }
}