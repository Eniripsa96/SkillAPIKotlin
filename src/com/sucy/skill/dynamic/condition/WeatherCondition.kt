package com.sucy.skill.dynamic.condition

import com.sucy.skill.dynamic.CastContext
import com.sucy.skill.facade.api.data.Weather
import com.sucy.skill.facade.api.entity.Actor

class WeatherCondition : Condition() {
    override val key = "weather"

    private var weather = "rain"

    override fun initialize() {
        super.initialize()

        weather = metadata.getString("weather", weather).toLowerCase()
    }

    override fun matches(context: CastContext, target: Actor, recipient: Actor): Boolean {
        val world = recipient.world
        val (x, y, z) = recipient.location.coords.toInt()

        val weather = world.getWeather(x, y, z)
        val temperature = world.getTemperature(x, y, z)

        return when(this.weather) {
            "thunder" -> weather == Weather.THUNDERING && temperature <= 1
            "rain" -> weather == Weather.RAINING && temperature > 0.15 && temperature <= 1
            "snow" -> weather == Weather.RAINING && temperature <= 0.15
            else -> weather == Weather.NONE || temperature > 1
        }
    }
}