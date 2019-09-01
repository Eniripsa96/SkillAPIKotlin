package com.sucy.skill.api.hook

/**
 * Interface that can be implemented by other plugins to provide
 * entity details to SkillAPI such as levels, mana, or other information.
 */
interface EntityDataSupplier {
    fun isKnownEntity(entityId: Int): Boolean { return true }
    fun getLevel(entityId: Int): Int { return 1 }
    fun getMana(entityId: Int): Double { return 0.0 }
}