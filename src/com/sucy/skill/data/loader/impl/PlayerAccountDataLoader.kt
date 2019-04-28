package com.sucy.skill.data.loader.impl

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.player.PlayerAccount
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.log.Logger

object PlayerAccountDataLoader : DataLoader<PlayerAccount> {
    private const val HEALTH = "health"
    private const val MANA = "mana"
    private const val FOOD = "food"
    private const val LOCATION = "pos"
    private const val INVENTORY = "items"
    private const val PROFESSIONS = "professions"
    private const val SKILLS = "skills"
    private const val SKILL_SOURCE = "source"

    override val requiredKeys: Array<String> = arrayOf()
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(data: Data): PlayerAccount {
        val result = PlayerAccount()

        result.health = getDouble(data, HEALTH)
        result.mana = getDouble(data, MANA)
        result.food = getDouble(data, FOOD)
        result.location = data.getSection(LOCATION)?.let(LocationDataLoader::load)
        // TODO - inventory saved per account
        //result.inventory = data.getString(INVENTORY)?.let { parse(it) }

        val professions = data.getOrCreateSection(PROFESSIONS)
        professions.keys().forEach { group ->
            professions.getSection(group)?.let {
                result.professionSet[group] = ProfessionProgressDataLoader.load(it)
            }
        }

        val skills = data.getOrCreateSection(SKILLS)
        skills.keys().forEach { key ->
            skills.getSection(key)?.let {
                result.skillSet.add(SkillProgressDataLoader.load(it))
            }
        }

        return result
    }

    override fun serialize(data: PlayerAccount): Data {
        val result = Data()

        data.health?.let { result.set(HEALTH, it) }
        data.mana?.let { result.set(MANA, it) }
        data.food?.let { result.set(FOOD, it) }
        data.location?.let { result.set(LOCATION, LocationDataLoader.serialize(it)) }
        // TODO - inventory saved per account
        //data.inventory?.let { result.set(INVENTORY, serialize(it) }

        val professions = result.createSection(PROFESSIONS)
        data.professionSet.all.forEach {
            professions.set(it.data.group, ProfessionProgressDataLoader.serialize(it))
        }

        val skills = result.createSection(SKILLS)
        data.skillSet.forEach {
            skills.set(it.data.key, SkillProgressDataLoader.serialize(it))
        }

        return result
    }

    private fun getDouble(data: Data, key: String): Double? {
        val result = data.getDouble(key, -1.0)
        return if (result < 0) null else result
    }
}