package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer

object SlotConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = mapOf("check-durability" to false, "slot-type" to "SLOT")
    override val newKey: String?
        get() = "item"
    override val conversions = listOf(
        LegacyConversion.Rename("slot", "slots"),
        LegacyConversion.Rename("check-mat", "check-material"),
        LegacyConversion.Copy("material"),
        LegacyConversion.Copy("check-data"),
        LegacyConversion.Copy("data"),
        LegacyConversion.Copy("check-lore"),
        LegacyConversion.Copy("lore"),
        LegacyConversion.Copy("check-name"),
        LegacyConversion.Copy("name"),
        LegacyConversion.Copy("regex")
    )
}