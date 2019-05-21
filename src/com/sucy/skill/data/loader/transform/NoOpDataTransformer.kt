package com.sucy.skill.data.loader.transform

import com.sucy.skill.util.io.Data

/**
 * Transformer that returns the data as-is, used for latest configurations matching the code
 */
object NoOpDataTransformer : DataTransformer {
    override fun transform(key: String, data: Data): Data {
        return data
    }
}