package com.sucy.skill.api.values

data class Cooldown(var duration: Double) {
    var time: Double = 0.0

    fun start() {
        time = System.currentTimeMillis() + duration * 1000.0
    }

    fun add(seconds: Double) {
        if (onCooldown()) time += seconds * 1000.0
        else time = System.currentTimeMillis() + seconds * 1000.0
    }

    fun subtract(seconds: Double) {
        add(-seconds)
    }

    fun onCooldown(): Boolean {
        return time > System.currentTimeMillis()
    }
}