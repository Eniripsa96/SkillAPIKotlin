package com.sucy.skill.util.io.parser

/**
 * SkillAPIKotlin © 2018
 */
enum class Parsers(val parser: Parser) {
    YAML(YAMLParser()),
    JSON(JsonParser())
}