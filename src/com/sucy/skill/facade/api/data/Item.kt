package com.sucy.skill.facade.api.data

import com.sucy.skill.facade.internal.data.InternalItem

/**
 * SkillAPIKotlin © 2018
 */
interface Item {
    var name: String?
    var lore: List<String>
    var type: String
    var amount: Int
    var durability: Short
    var data: Byte
    var visibility: Int
    var tags: Map<String, Any>

    fun copy(
            name: String? = this.name,
            lore: List<String> = this.lore,
            type: String = this.type,
            amount: Int = this.amount,
            durability: Short = this.durability,
            data: Byte = this.data,
            visibility: Int = this.visibility,
            tags: Map<String, Any> = this.tags
    ) : Item {
        return InternalItem(
                type = type,
                durability = durability,
                data = data,
                amount = amount,
                name = name,
                lore = lore,
                visibility = visibility,
                tags = tags
        )
    }
}