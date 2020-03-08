package com.sucy.skill.data.loader.transform.legacy

import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

interface LegacyTransformer : DataTransformer {
    val conversions: List<LegacyConversion>
    val defaultValues: Map<String, Any>

    override fun transform(key: String, data: Data): Data = generateNewData(data)

    fun generateNewData(oldData: Data): Data {
        val newData = Data()
        conversions.forEach { it.apply(oldData, newData) }
        return newData
    }
}

interface LegacyConversion {
    val field: String
    fun apply(oldData: Data, newData: Data)

    class Copy(override val field: String) : LegacyConversion {
        override fun apply(oldData: Data, newData: Data) {
            oldData.get(field)?.let { newData.set(field, it) }
        }
    }

    class Rename(override val field: String, val newName: String) : LegacyConversion {
        override fun apply(oldData: Data, newData: Data) {
            val value = oldData.get(field) ?: return
            newData.set(newName, value)
        }
    }

    class Scaling(override val field: String, val newName: String = field) : LegacyConversion {
        override fun apply(oldData: Data, newData: Data) {
            if (!oldData.has("$field-base") && !oldData.has(field)) return

            val base = oldData.getDouble("$field-base", oldData.getDouble(field))
            val scale = oldData.getDouble("$field-scale")

            when {
                scale == 0.0 -> newData.set(newName, base)
                base == 0.0 -> newData.set(newName, "lvl * $scale")
                else -> newData.set(newName, "$base + lvl * $scale")
            }
        }
    }

    class Bool(override val field: String, val newName: String = field, val match: String, val negative: Boolean = true) : LegacyConversion {
        override fun apply(oldData: Data, newData: Data) {
            val matches = oldData.getString(field, "").equals(match, true)
            newData.set(newName, matches != negative)
        }
    }

    class Custom(override val field: String, val handler: (Data, Data, String) -> Unit) : LegacyConversion {
        override fun apply(oldData: Data, newData: Data) = handler(oldData, newData, field)
    }
}