package com.sucy.skill

import com.sucy.skill.api.SkillPlugin
import com.sucy.skill.api.profession.Profession
import com.sucy.skill.api.skill.Skill
import com.sucy.skill.data.loader.impl.profession.DynamicProfessionDataLoader
import com.sucy.skill.data.loader.impl.skill.DynamicSkillDataLoader
import com.sucy.skill.util.io.Config
import com.sucy.skill.util.io.ConfigHolder
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.log.Logger
import java.io.File

class Registry {
    private val skillsByKey = HashMap<String, Skill>()
    private val skillsByName = HashMap<String, Skill>()
    private val professionsByKey = HashMap<String, Profession>()
    private val professionsByName = HashMap<String, Profession>()

    init {
        val plugins = SkillAPI.server.plugins.filter { it is SkillPlugin }.map { it as SkillPlugin }

        loadDynamicSkills()
        loadDynamicProfessions()

        forEach(plugins) { it.getSkills().forEach(this::addSkill) }
        forEach(plugins) { it.getProfessions().forEach(this::addProfession) }

        Logger.info("Registration Complete")
        Logger.info(" > ${skillsByName.size} Skills")
        Logger.info(" > ${professionsByName.size} Classes")
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

    private fun loadDynamicSkills() {
        loadDynamic(
                rootFile = "dynamic/skill",
                largeFile = "dynamic/skills",
                handler = this::loadDynamicSkill
        )
    }

    private fun loadDynamicProfessions() {
        loadDynamic(
                rootFile = "dynamic/class",
                largeFile = "dynamic/classes",
                handler = this::loadDynamicProfession
        )
    }

    private fun loadDynamic(rootFile: String, largeFile: String, handler: (String, Data) -> Unit) {
        val root = File(SkillAPI.plugin.getConfigFolder(), rootFile)
        root.mkdirs()

        val largeConfig = Config(SkillAPI.plugin, largeFile)
        if (!largeConfig.data.getBoolean(LOADED, false)) {
            largeConfig.data.forEach(setOf(LOADED)) { key ->
                largeConfig.data.getSection(key)?.let { handler(key, it) }
            }
        }

        traverse(SkillAPI.plugin, root) { name, config ->
            handler(name, config.data)
        }
    }

    private fun traverse(configHolder: ConfigHolder, folder: File, callback: (String, Config) -> Unit) {
        val root = File(configHolder.getConfigFolder()).absolutePath
        folder.listFiles().forEach {
            if (it.isDirectory) {
                traverse(configHolder, folder, callback)
            } else if (it.name.endsWith(".yml")) {
                val relative = it.absolutePath.substring(root.length + 1)
                val withoutExtension = relative.substring(0, relative.length - 4);
                val config = Config(configHolder, withoutExtension)
                callback(it.nameWithoutExtension, config)
            }
        }
    }

    private fun loadDynamicSkill(key: String, data: Data) {
        try {
            val skill = DynamicSkillDataLoader.transformAndLoad(key, data)
            addSkill(skill)
        } catch (ex: Exception) {
            Logger.warn("Failed to laod skill $key")
            ex.printStackTrace()
        }
    }

    private fun loadDynamicProfession(key: String, data: Data) {
        try {
            val profession = DynamicProfessionDataLoader.transformAndLoad(key, data)
            addProfession(profession)
        } catch (ex: Exception) {
            Logger.warn("Failed to load profession $key")
            ex.printStackTrace()
        }
    }

    companion object {
        const val LOADED = "loaded"
    }
}