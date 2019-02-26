package com.sucy.skill.data.loader

import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.data.loader.transform.NoOpDataTransformer
import com.sucy.skill.util.io.Data

interface DataLoader<T> {
    val requiredKeys: Array<String>
    val transformers: Map<Int, DataTransformer>

    fun load(data: Data, version: Int) : T {
        val transformer = transformers.getOrDefault(version, NoOpDataTransformer())
        val config = transformer.transform(data)

        requiredKeys.forEach {
            if (!config.has(it)) {
                throw InvalidDataException("Key \"$it\" is required")
            }
        }

        return load(config)
    }

    fun load(data: Data) : T
}