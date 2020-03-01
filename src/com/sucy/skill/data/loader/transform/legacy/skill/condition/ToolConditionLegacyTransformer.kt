package com.sucy.skill.data.loader.transform.legacy.skill.condition

import com.sucy.skill.data.loader.transform.legacy.LegacyConversion
import com.sucy.skill.data.loader.transform.legacy.skill.LegacyEffectTransformer
import com.sucy.skill.facade.api.data.inventory.VanillaItemType

object ToolConditionLegacyTransformer : LegacyEffectTransformer {
    override val defaultValues = mapOf("slot-type" to "MAIN_HAND", "check-mat" to true)
    override val conversions = listOf(
        LegacyConversion.Custom("") { oldData, newData, _ ->
            val material = oldData.getString("material", "any")
            val materialSet = if (material.equals("any", true)) {
                VanillaItemType.values().toList()
            } else {
                VanillaItemType.values().filter { it.name.startsWith(material.toUpperCase()) }
            }

            val tool = oldData.getString("tool", "any")
            val toolSet = if (tool.equals("any", true)) {
                VanillaItemType.values().toList()
            } else {
                VanillaItemType.values().filter { it.name.endsWith("_${tool.toUpperCase()}") }
            }

            val intersection = materialSet.intersect(toolSet)
            newData.set("material", intersection.map { it.name })
        }
    )
}