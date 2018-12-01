package com.sucy.skill.facade.internal.data

import com.sucy.skill.facade.api.data.Item

/**
 * SkillAPIKotlin © 2018
 */
class InternalItem(
        override var type: String,
        override var durability: Short = 0,
        override var data: Byte = 0,
        override var amount: Int = 1,
        override var name: String? = null,
        override var lore: List<String> = ArrayList(),
        override var visibility: Int = 0,
        override var tags: Map<String, Any> = HashMap()
) : Item