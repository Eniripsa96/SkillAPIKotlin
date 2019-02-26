package com.sucy.skill.api.values

/**
 * SkillAPIKotlin © 2018
 */
class FlagSet {
    private val flags = HashSet<String>()

    fun isActive(flag: String): Boolean {
        return flags.contains(flag)
    }

    fun set(flag: String): Boolean {
        return flags.add(flag)
    }

    fun clear(flag: String): Boolean {
        return flags.remove(flag)
    }
}
