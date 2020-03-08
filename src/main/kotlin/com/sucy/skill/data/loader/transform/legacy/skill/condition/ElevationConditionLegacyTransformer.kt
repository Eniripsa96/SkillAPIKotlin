package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object ElevationConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val newKey: String?
        get() = "value"
    override val conversions = listOf(
        LegacyConversion.Custom("type") { oldData, newData, name ->
            when (oldData.getString(name, "").toLowerCase()) {
                "difference" -> newData.set("value", "target.yPos - yPos")
                else -> newData.set("value", "target.yPos")
            }
        },
        LegacyConversion.Scaling("min-value", "min"),
        LegacyConversion.Scaling("max-value", "max")
    )
}