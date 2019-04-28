package com.sucy.skill

import com.sucy.skill.api.SkillPlugin
import com.sucy.skill.api.profession.Profession
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.util.log.Logger

class Registry {
    private val skillsByKey = HashMap<String, Skill>()
    private val skillsByName = HashMap<String, Skill>()
    private val professionsByKey = HashMap<String, Profession>()
    private val professionsByName = HashMap<String, Profession>()

    init {
        val plugins = SkillAPI.server.plugins.filter { it is SkillPlugin }.map { it as SkillPlugin }

        forEach(plugins) { it.getSkills().forEach(this::addSkill) }
        forEach(plugins) { it.getProfessions().forEach(this::addProfession) }
    }

    fun isValidSkill(key: String): Boolean = getSkill(key) != null
    fun isValidProfession(key: String): Boolean = getProfession(key) != null

    fun getSkill(key: String): Skill? {
        val lower = key.toLowerCase()
        return skillsByName[lower] ?: skillsByKey[lower]
    }

    fun getProfession(key: String): Profession? {
        val lower = key.toLowerCase()
        return professionsByName[lower] ?: professionsByKey[lower]
    }

    private fun addSkill(skill: Skill) {
        if (skillsByName.containsKey(skill.name)) {
            Logger.warn { "Duplicate skill name ${skill.name} - only the first will be kept" }
        } else {
            if (skillsByKey.containsKey(skill.key)) {
                Logger.warn { "Duplicate skill key ${skill.key} - names are different so both will be kept" }
            }

            // TODO - config file

            SkillAPI.eventBus.register(SkillAPI.plugin, skill)
            skillsByName[skill.name.toLowerCase()] = skill
            skillsByKey[skill.key.toLowerCase()] = skill
        }
    }

    private fun addProfession(profession: Profession) {
        if (professionsByName.containsKey(profession.name)) {
            Logger.warn { "Duplicate profession name ${profession.name} - only the first will be kept"}
        } else {
            if (professionsByKey.containsKey(profession.key)) {
                Logger.warn { "Duplicate profession key ${profession.key} - names are different so both will be kept" }
            }

            // TODO - config file

            SkillAPI.eventBus.register(SkillAPI.plugin, profession)
            professionsByName[profession.name.toLowerCase()] = profession
            professionsByKey[profession.key.toLowerCase()] = profession
        }
    }

    private fun forEach(plugins: List<SkillPlugin>, handler: (SkillPlugin) -> Unit) {
        plugins.forEach {
            try {
                handler.invoke(it)
            } catch (ex: Exception) {
                Logger.error("Plugin ${it::class.simpleName} failed during SkillAPI registration")
                ex.printStackTrace()
            }
        }
    }
}