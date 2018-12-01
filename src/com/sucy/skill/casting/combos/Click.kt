package com.sucy.skill.casting.combos

/**
 * SkillAPIKotlin © 2018
 */
enum class Click(val id: Int, val key: String) {
    NULL(0, "NULL"),
    LEFT(1, "L"),
    RIGHT(2, "R"),
    SHIFT(3, "S"),
    LEFT_SHIFT(4, "LS"),
    RIGHT_SHIFT(5, "RS"),
    SPACE(6, "P"),
    Q(7, "Q");

    fun configKey(): String {
        return name.toLowerCase().replace('_', '-')
    }

    companion object {
        const val BITS = 3
        const val BIT_MASK = (1 shl BITS) - 1
        const val MAX_COMBO_SIZE = 30 / BITS
    }
}