package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer
import com.sucy.skill.data.loader.transform.legacy.LegacyConversion

object BlockConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Copy("types"),
        LegacyConversion.Copy("standing")
    )
}