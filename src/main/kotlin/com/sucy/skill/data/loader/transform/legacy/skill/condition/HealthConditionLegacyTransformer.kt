package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object HealthConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Scaling("min-value", "min"),
        LegacyConversion.Scaling("max-value", "max"),
        LegacyConversion.Custom("type") { oldData, newData, name ->
            val formula = when (oldData.getString("type", "").toLowerCase()) {
                "difference percent" -> "(target.health - health) * 100 / health"
                "difference" -> "target.health - health"
                "percent" -> "target.health * 100 / target.maxHealth"
                else -> "target.health"
            }
            newData.set("value", formula)
        }
    )
}