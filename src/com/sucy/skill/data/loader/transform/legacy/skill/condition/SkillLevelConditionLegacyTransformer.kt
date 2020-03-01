package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object SkillLevelConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Copy("skill"),
        LegacyConversion.Scaling("min-level", "min"),
        LegacyConversion.Scaling("max-level", "max")
    )
}