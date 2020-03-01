package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object ManaConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Scaling("min-value", "min"),
        LegacyConversion.Scaling("max-value", "max"),
        LegacyConversion.Custom("type") { oldData, newData, name ->
            val formula = when (oldData.getString(name, "").toLowerCase()) {
                "difference percent" -> "(target.mana - mana) * 100 / mana"
                "difference" -> "target.mana - mana"
                "percent" -> "target.mana * 100 / target.maxMana"
                else -> "target.mana"
            }
            newData.set("value", formula)
        }
    )
}