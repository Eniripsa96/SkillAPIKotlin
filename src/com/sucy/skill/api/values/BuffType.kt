package com.sucy.skill.api.values

enum class BuffType(val key: String) {
    DAMAGE("api.buff.dmg"),
    DEFENSE("api.buff.def"),
    SKILL_DAMAGE("api.buff.sdmg"),
    SKILL_DEFENSE("api.buff.sdef"),
    HEALING("api.buff.heal");

    fun getKey(category: String?): String {
        return if (category == null || category.isBlank()) {
            key
        } else {
            "$key.$category"
        }
    }
}