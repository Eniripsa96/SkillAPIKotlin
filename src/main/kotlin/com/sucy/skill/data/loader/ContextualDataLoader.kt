package com.sucy.skill.data.loader

import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.data.loader.transform.NoOpDataTransformer
import com.sucy.skill.util.io.Config
import com.sucy.skill.util.io.Data

interface ContextualDataLoader<T> {
    val transformers: Map<Int, DataTransformer>
    val latestVersion: Int
        get() = 2

    fun transformAndLoad(key: String, data: Data, context: T): T {
        val version = data.getVersion()
        return transformAndLoad(key, data, version, context)
    }

    fun transformAndLoad(key: String, data: Data, version: Int, context: T): T {
        val transformer = transformers.getOrDefault(version, NoOpDataTransformer)
        val config = transformer.transform(key, data)
        return load(key, config, context)
    }

    fun load(key: String, data: Data, context: T) : T

    fun writeTo(config: Config, data: T) {
        val serialized = serialize(data)
        serialized.setVersion(latestVersion)
        config.data = serialized
        config.save()
    }

    fun serialize(data: T): Data

    fun getKey(data: T): String? = null
}