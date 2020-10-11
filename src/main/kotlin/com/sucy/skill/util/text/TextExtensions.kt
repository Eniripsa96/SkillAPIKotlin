package com.sucy.skill.util.text

/**
 * SkillAPIKotlin © 2018
 */

private val COLOR_REGEX = Regex("&([0-9a-fk-orA-FL-OR])")
private val STRIP_REGEX = Regex("§.?")
private val SPLIT_REGEX = Regex("[ _-]")
private val INT_REGEX = Regex("[+-]?[0-9]+")
private val NUMBER_REGEX = Regex("[+-]?([0-9]+|[0-9]*[,.][0-9]+)")

/**
 * Uses & characters to color strings using Minecraft formatting
 */
fun String.color(): String {
    return this.replace(COLOR_REGEX, "§$1")
}

/**
 * Removes color characters from a string, replacing them with &
 */
fun String.uncolor(): String {
    return this.replace('§', '&')
}

/**
 * Removes all traces of color from a string, removing the color characters
 * and the proceeding digit
 */
fun String.stripColor(): String {
    return this.replace(STRIP_REGEX, "")
}

/**
 * Applies [String.color] on each element of the list
 */
fun List<String>.color(): List<String> {
    return this.map { it.color() }
}

/**
 * Applies [String.uncolor] on each element of the list
 */
fun List<String>.uncolor(): List<String> {
    return this.map { it.uncolor() }
}

/**
 * Applies [String.stripColor] on each element of the list
 */
fun List<String>.stripColor(): List<String> {
    return this.map { it.stripColor() }
}

/**
 * Formats [this] into title case (e.g. This Is Title Case)
 */
fun String.titleCase(): String {
    return SPLIT_REGEX.split(this, 0)
            .joinToString(separator = " ") { it[0].toUpperCase() + it.substring(1).toLowerCase() }
}

/**
 * Formats [this] into camel case (e.g. ThisIsCamelCase)
 */
fun String.camelCase(): String {
    return SPLIT_REGEX.split(this, -1)
            .joinToString { it[0].toUpperCase() + it.substring(1).toLowerCase() }
}

/**
 * Formats a string to match standard enum formatting (e.g. THIS_IS_AN_ENUM_NAME)
 */
fun String.enumName(): String {
   return this.toUpperCase().replace(' ', '_')
}

fun String.isInteger(): Boolean = INT_REGEX.matches(this)
fun String.isNumber(): Boolean = NUMBER_REGEX.matches(this)
fun String.isBoolean(): Boolean = this == "true" || this == "false"