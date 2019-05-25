package com.sucy.skill.data.loader.impl.common

import com.sucy.skill.api.values.ValueSet
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

object ValueSetDataLoader : DataLoader<ValueSet> {
    override val requiredKeys = arrayOf<String>()
    override val transformers = mapOf<Int, DataTransformer>()

    override fun load(key: String, data: Data): ValueSet {
        val result = ValueSet()
        data.forEach { result.values[it] = ValueDataLoader.load(it, data.getOrCreateSection(it)) }
        return result
    }

    override fun serialize(data: ValueSet): Data {
        val result = Data()
        data.values.forEach { result.set(it.key, ValueDataLoader.serialize(it.value)) }
        return result
    }
}