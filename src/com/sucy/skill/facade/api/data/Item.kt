package com.sucy.skill.facade.api.data

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
}