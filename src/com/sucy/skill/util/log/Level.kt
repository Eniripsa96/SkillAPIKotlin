package com.sucy.skill.util.log

enum class Level(val rank: Int) {
    TRACE(5),
    DEBUG(4),
    INFO(3),
    WARN(2),
    ERROR(1),
    NONE(0)
}