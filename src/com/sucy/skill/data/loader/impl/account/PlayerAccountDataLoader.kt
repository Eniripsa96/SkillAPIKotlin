package com.sucy.skill.data.loader.impl.account

import com.sucy.skill.api.player.PlayerAccount
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.impl.common.LocationDataLoader
import com.sucy.skill.data.loader.impl.profession.ProfessionProgressDataLoader
import com.sucy.skill.data.loader.impl.skill.SkillProgressDataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

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

    override fun load(key: String, data: Data): PlayerAccount {
        val result = PlayerAccount()

        result.health = getDouble(data, HEALTH)
        result.mana = getDouble(data, MANA)
        result.food = getDouble(data, FOOD)
        result.location = LocationDataLoader.load(LOCATION, data.getOrCreateSection(LOCATION))
        // TODO - inventory saved per account
        //result.inventory = data.getString(INVENTORY)?.let { parse(it) }

        val professions = data.getOrCreateSection(PROFESSIONS)
        professions.forEach { group ->
            professions.getSection(group)?.let {
                result.professionSet[group] = ProfessionProgressDataLoader.load(group, it)
            }
        }

        val skills = data.getOrCreateSection(SKILLS)
        skills.forEach { skillKey ->
            skills.getSection(skillKey)?.let {
                result.skillSet.add(SkillProgressDataLoader.load(skillKey, it))
            }
        }

        return result
    }

    override fun serialize(data: PlayerAccount): Data {
        val result = Data()

        result.set(HEALTH, data.health)
        result.set(MANA, data.mana)
        result.set(FOOD, data.food)
        result.set(LOCATION, LocationDataLoader.serialize(data.location))
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

    private fun getDouble(data: Data, key: String): Double {
        return data.getDouble(key, -1.0)
    }
}