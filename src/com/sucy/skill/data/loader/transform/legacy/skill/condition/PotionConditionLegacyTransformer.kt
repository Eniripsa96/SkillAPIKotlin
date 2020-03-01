package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object PotionConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Bool("type", "negated", "not active"),
        LegacyConversion.Rename("potion", "type"),
        LegacyConversion.Scaling("min-rank", "min"),
        LegacyConversion.Scaling("max-rank", "max")
    )
}