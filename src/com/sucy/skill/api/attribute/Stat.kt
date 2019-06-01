package com.sucy.skill.api.attribute

enum class Stat(val key: String) {
    HEALTH("health"),
    MANA("mana"),
    MANA_REGEN("mana-regen"),
    PHYSICAL_DAMAGE("physical-damage"),
    MELEE_DAMAGE("melee-damage"),
    PROJECTILE_DAMAGE("projectile-damage"),
    PHYSICAL_DEFENSE("physical--defense"),
    MELEE_DEFENSE("melee-defense"),
    PROJECTILE_DEFENSE("projectile-defense"),
    SKILL_DAMAGE("skill-damage"),
    SKILL_DEFENSE("skill-defense"),
    MOVE_SPEED("move-speed"),
    ATTACK_SPEED("attack-speed"),
    ARMOR("armor"),
    LUCK("luck"),
    ARMOR_TOUGHNESS("armor-toughness"),
    EXPERIENCE("experience"),
    HUNGER("hunger-heal"),
    COOLDOWN("cooldown"),
    KNOCKBACK_RESIST("knockback-resist")
}