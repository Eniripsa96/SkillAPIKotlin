package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer
import com.sucy.skill.data.loader.transform.legacy.LegacyConversion

object CastLevelLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Rename("min-level", "min"),
        LegacyConversion.Rename("max-level", "max")
    )
}