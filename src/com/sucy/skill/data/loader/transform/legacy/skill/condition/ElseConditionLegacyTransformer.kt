package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object ElseConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = mapOf("legacy" to "true")
    override val conversions = emptyList<LegacyConversion>()
}