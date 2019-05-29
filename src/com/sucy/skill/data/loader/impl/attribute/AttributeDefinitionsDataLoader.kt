package com.sucy.skill.data.loader.impl.attribute

import com.sucy.skill.api.attribute.Attribute
import com.sucy.skill.api.attribute.AttributeDefinitions
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.legacy.LegacyAttributeDefinitionsTransformer
import com.sucy.skill.util.io.Data

object AttributeDefinitionsDataLoader : DataLoader<AttributeDefinitions> {
    override val requiredKeys = arrayOf<String>()
    override val transformers = mapOf(1 to LegacyAttributeDefinitionsTransformer)

    override fun load(key: String, data: Data): AttributeDefinitions {
        val attributes = ArrayList<Attribute>()
        data.forEach { attributes.add(AttributeDataLoader.load(it, data.getOrCreateSection(it))) }
        return AttributeDefinitions(attributes.toList())
    }

    override fun serialize(data: AttributeDefinitions): Data {
        val result = Data()
        data.attributes.forEach { result.set(it.key, AttributeDataLoader.serialize(it)) }
        return result
    }
}