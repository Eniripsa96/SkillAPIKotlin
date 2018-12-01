package com.sucy.skill.util.text

/**
 * SkillAPIKotlin © 2018
 */

private val COLOR_REGEX = Regex("&([0-9a-fk-orA-FL-OR])")
private val STRIP_REGEX = Regex("§.?")

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