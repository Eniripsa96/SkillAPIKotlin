package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer
import com.sucy.skill.data.loader.transform.legacy.LegacyConversion

object NameConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Rename("str", "name"),
        LegacyConversion.Bool("contains", "negated", "false"),
        LegacyConversion.Custom("regex") { oldData, newData, name ->
            val comparison = if (oldData.getBoolean("regex")) "REGEX" else "CONTAINS"
            newData.set("comparison", comparison)
        }
    )
}