package com.sucy.skill.util.io.parser

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class JsonParser : Parser() {
    private val mapType = object : TypeToken<Map<String, Any>>() { }.type
    private val gson = GsonBuilder().setLenient().create()

    override fun parse(data: String, quote: Char): Data {
        val map = gson.fromJson<Map<String, Any>>(data, mapType)
        return Data(map)
    }

    override fun serialize(data: Data, builder: StringBuilder, indent: Int, quote: Char) {
        builder.append(gson.toJson(data.asMap()))
    }
}
