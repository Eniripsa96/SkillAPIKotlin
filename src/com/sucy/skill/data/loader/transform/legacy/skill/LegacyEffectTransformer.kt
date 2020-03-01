package com.sucy.skill.data.loader.transform.legacy.skill

import com.sucy.skill.data.loader.transform.legacy.LegacyTransformer
import com.sucy.skill.util.io.Data

interface LegacyEffectTransformer : LegacyTransformer {
    val newKey: String?
        get() = null

    override fun transform(key: String, data: Data): Data {
        val result = super.transform(key, data)
        result.set("_key", newKey ?: key)
        return result
    }
}