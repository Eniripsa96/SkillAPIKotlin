package com.sucy.skill.api.values

class Cooldown {
    var time: Double = 0.0

    fun start(seconds: Double) {
        time = System.currentTimeMillis() + seconds * 1000
    }

    fun add(seconds: Double) {
        if (onCooldown()) time += seconds * 1000
        else time = System.currentTimeMillis() + seconds * 1000
    }

    fun subtract(seconds: Double) {
        add(-seconds)
    }

    fun onCooldown(): Boolean {
        return time > System.currentTimeMillis()
    }
}