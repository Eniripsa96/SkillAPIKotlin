package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object TimeConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = emptyMap<String, Any>()
    override val conversions = listOf(
        LegacyConversion.Custom("time") { oldData, newData, name ->
            if (!oldData.getString(name, "").equals("night", true)) {
                newData.set("negated", true)
            }
            newData.set("min", 12300)
            newData.set("max", 23850)
        }
    )
}