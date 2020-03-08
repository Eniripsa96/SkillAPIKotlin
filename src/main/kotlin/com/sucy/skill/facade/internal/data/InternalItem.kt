package com.sucy.skill.facade.internal.data

import com.sucy.skill.facade.api.data.inventory.Item
import com.sucy.skill.facade.api.data.inventory.ItemType

/**
 * SkillAPIKotlin © 2018
 */
data class InternalItem(
        override var type: ItemType,
        override var durability: Short = 0,
        override var data: Byte = 0,
        override var amount: Int = 1,
        override var name: String? = null,
        override var lore: List<String> = mutableListOf(),
        override var visibility: Int = 0,
        override var tags: Map<String, Any> = mutableMapOf()
) : Item