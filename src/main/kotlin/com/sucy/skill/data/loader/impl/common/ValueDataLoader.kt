package com.sucy.skill.data.loader.impl.common

import com.sucy.skill.api.values.Value
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

object ValueDataLoader : DataLoader<Value> {
    const val BASE = "base"
    const val BONUS = "bonus"
    const val MULTIPLIER = "multiplier"

    override val requiredKeys = arrayOf<String>()
    override val transformers = mapOf<Int, DataTransformer>()

    override fun load(key: String, data: Data): Value {
        val result = Value()
        loadSection(data, BASE, result::addBase)
        loadSection(data, BONUS, result::addBonus)
        loadSection(data, MULTIPLIER, result::addMultiplier)
        return result
    }

    private fun loadSection(data: Data, key: String, setter: (Double, String) -> Unit) {
        val section = data.getOrCreateSection(key)
        section.forEach { source -> setter(section.getDouble(source), source) }
    }

    override fun serialize(data: Value): Data {
        val result = Data()
        saveSection(result, BASE, data.baseSources)
        saveSection(result, BONUS, data.additiveSources)
        saveSection(result, MULTIPLIER, data.multiplierSources)
        return result
    }

    private fun saveSection(data: Data, key: String, sources: Map<String, Double>) {
        val section = data.createSection(key)
        sources.forEach { section.set(it.key, it.value) }
    }
}