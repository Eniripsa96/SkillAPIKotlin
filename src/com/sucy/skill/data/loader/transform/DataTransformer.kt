package com.sucy.skill.data.loader.transform

import com.sucy.skill.util.io.Data

interface DataTransformer {
    fun transform(key: String, data: Data) : Data
}