package com.sucy.skill.api.values

enum class TimerType {
    // Latest modifier from the same source overwrites any previous modifier
    OVERWRITE,
    // Highest modifier overwrites other modifiers
    HIGHEST,
    // Lowest modifier overwrites other modifiers
    LOWEST,
    // Each modifier runs on its own timer
    SEPARATE,
    // Each modifier runs on its own timer but only the highest is applied
    SEPARATE_HIGHEST,
    // Each modifier runs on its own timer but only the lowest is applied
    SEPARATE_LOWEST,
    // Each modifier runs on its own timer but only the oldest value is used
    SEPARATE_OLDEST
}