package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class ItemSettings(config: Data) {
    val enableLore = config.getBoolean("lore-requirements")
    val enableAttributes = config.getBoolean("lore-attributes")
    val enableSkills = config.getBoolean("skill-requirements")
    val enableDroppingHeldItem = config.getBoolean("drop-weapon")
    val classReqText = config.getString("lore-class-text")
    val levelReqText = config.getString("lore-level-text")
    val excludedClassReqText = config.getString("lore-exclude-text")
    val skillReqText = config.getString("lore-skill-text")
    val attributeReqText = config.getString("lore-attribute-text")
    val attributeBonusText = config.getStringList("attribute-text")
    val itemSlotsToCheck = config.getStringList("slots").stream()
            .mapToInt(Integer::parseInt)
            .toArray()
}