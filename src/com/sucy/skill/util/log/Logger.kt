package com.sucy.skill.util.log

object Logger {
    private var level = Level.INFO

    fun setLevel(level: Level) {
        this.level = level
    }

    fun trace(message: String) {
        print(Level.TRACE, message)
    }

    fun trace(message: () -> String) {
        print(Level.TRACE, message)
    }

    fun debug(message: String) {
        print(Level.DEBUG, message)
    }

    fun debug(message: () -> String) {
        print(Level.DEBUG, message)
    }

    fun info(message: String) {
        print(Level.INFO, message)
    }

    fun info(message: () -> String) {
        print(Level.INFO, message)
    }

    fun warn(message: String) {
        print(Level.WARN, message)
    }

    fun warn(message: () -> String) {
        print(Level.WARN, message)
    }

    fun error(message: String) {
        print(Level.ERROR, message)
    }

    fun error(message: () -> String) {
        print(Level.ERROR, message)
    }

    private fun print(level: Level, message: String) {
        if (this.level.rank >= level.rank) {
            println("SkillAPI [$level] $message")
        }
    }

    private fun print(level: Level, message: () -> String) {
        if (this.level.rank >= level.rank) {
            println("SkillAPI [$level] ${message.invoke()}")
        }
    }
}
