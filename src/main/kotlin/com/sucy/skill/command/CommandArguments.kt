package com.sucy.skill.command

data class ArgumentPattern(val arguments: List<ArgumentDefinition>) {
    private val required = arguments.count { !it.optional }

    init {
        val multiCount = arguments.count { !it.single }
        require(multiCount <= 1) { "Cannot have more than one endless arguments" }
        require(multiCount == 0 || arguments.none { it.optional && it.single }) {
            "Cannot have optional arguments with endless arguments"
        }
    }

    fun hasEnoughArguments(input: List<String>) = input.size >= required

    fun isValidArguments(input: List<String>) = hasEnoughArguments(input) && (parse(input).isNotEmpty() || input.isEmpty())

    fun parse(input: List<String>): MutableMap<String, String> {
        val result = mutableMapOf<String, String>()
        var index = 0
        var optionalCount = 0
        arguments.forEachIndexed { i, arg ->
            if (index >= input.size) return result
            when {
                !arg.single -> {
                    var toTake = input.size - arguments.size + 1
                    val start = index
                    while (toTake > 0 && arg.condition(input[index])) {
                        index++
                        toTake--
                    }
                    if (start == index) null
                    else input.subList(start, index).joinToString(separator = " ")
                }
                arg.optional -> {
                    if (required + optionalCount < input.size && arg.condition(input[index])) {
                        optionalCount++
                        index++
                        input[index - 1]
                    } else null
                }
                else -> {
                    if (!arg.condition(input[index])) return mutableMapOf()
                    index++
                    input[index - 1]
                }
            }?.let { result[arg.name] = it }
        }
        return result
    }
}

data class ArgumentDefinition(
    val name: String,
    val optional: Boolean = false,
    val single: Boolean = true,
    val condition: (String) -> Boolean = { true }
)