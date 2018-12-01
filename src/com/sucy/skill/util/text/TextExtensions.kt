package com.sucy.skill.util.text

/**
 * SkillAPIKotlin © 2018
 */

private val COLOR_REGEX = Regex("&([0-9a-fk-orA-FL-OR])")
private val STRIP_REGEX = Regex("§.?")
private val SPLIT_REGEX = Regex("[ _-]")

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
 * Formats [this] into title case (e.g. This Is Title Case)
 */
fun String.titleCase(): String {
    return SPLIT_REGEX.split(this, -1)
            .joinToString(separator = " ") { it[0].toUpperCase() + it.substring(1).toLowerCase() }
}

/**
 * Formats [this] into camel case (e.g. ThisIsCamelCase)
 */
fun String.camelCase(): String {
    return SPLIT_REGEX.split(this, -1)
            .joinToString { it[0].toUpperCase() + it.substring(1).toLowerCase() }
}