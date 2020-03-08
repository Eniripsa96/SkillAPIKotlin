package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object LoreConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = mapOf(
        "check-durability" to false,
        "check-mat" to false,
        "check-data" to false,
        "check-name" to false,
        "slot-type" to "MAIN_HAND"
    )
    override val newKey: String?
        get() = "item"
    override val conversions = listOf(
        LegacyConversion.Rename("str", "lore"),
        LegacyConversion.Copy("regex")
    )
}