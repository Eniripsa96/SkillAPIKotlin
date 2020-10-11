package com.sucy.skill.command

import io.kotlintest.shouldBe
import org.junit.Test

class ArgumentPatternTest {

    @Test
    fun hasEnoughArgumentsBasic() {
        BASIC_PATTERN.hasEnoughArguments(argList(0)) shouldBe false
        BASIC_PATTERN.hasEnoughArguments(argList(1)) shouldBe false
        BASIC_PATTERN.hasEnoughArguments(argList(2)) shouldBe true
        BASIC_PATTERN.hasEnoughArguments(argList(3)) shouldBe true
        BASIC_PATTERN.hasEnoughArguments(argList(100)) shouldBe true
    }

    @Test
    fun hasEnoughArgumentsWithOptionalArguments() {
        OPTIONAL_PATTERN.hasEnoughArguments(argList(0)) shouldBe false
        OPTIONAL_PATTERN.hasEnoughArguments(argList(1)) shouldBe false
        OPTIONAL_PATTERN.hasEnoughArguments(argList(2)) shouldBe true
        OPTIONAL_PATTERN.hasEnoughArguments(argList(5)) shouldBe true
        OPTIONAL_PATTERN.hasEnoughArguments(argList(100)) shouldBe true
    }

    @Test
    fun hasEnoughArgumentsWithEndlessRequired() {
        listOf(ENDLESS_END_REQUIRED_PATTERN, ENDLESS_FRONT_REQUIRED_PATTERN, ENDLESS_MIDDLE_REQUIRED_PATTERN).forEach {
            it.hasEnoughArguments(argList(0)) shouldBe false
            it.hasEnoughArguments(argList(1)) shouldBe false
            it.hasEnoughArguments(argList(2)) shouldBe false
            it.hasEnoughArguments(argList(3)) shouldBe true
            it.hasEnoughArguments(argList(5)) shouldBe true
            it.hasEnoughArguments(argList(100)) shouldBe true
        }
    }

    @Test
    fun hasEnoughArgumentsWithEndlessOptional() {
        listOf(ENDLESS_END_OPTIONAL_PATTERN, ENDLESS_FRONT_OPTIONAL_PATTERN, ENDLESS_MIDDLE_OPTIONAL_PATTERN).forEach {
            it.hasEnoughArguments(argList(0)) shouldBe false
            it.hasEnoughArguments(argList(1)) shouldBe false
            it.hasEnoughArguments(argList(2)) shouldBe true
            it.hasEnoughArguments(argList(3)) shouldBe true
            it.hasEnoughArguments(argList(5)) shouldBe true
            it.hasEnoughArguments(argList(100)) shouldBe true
        }
    }

    @Test
    fun parse() {
        BASIC_PATTERN.parse(argList(2)) shouldBe mapOf("a" to "0", "b" to "1")

        OPTIONAL_PATTERN.parse(argList(2)) shouldBe mapOf("b" to "0", "d" to "1")
        OPTIONAL_PATTERN.parse(argList(3)) shouldBe mapOf("a" to "0", "b" to "1", "d" to "2")
        OPTIONAL_PATTERN.parse(argList(4)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2", "d" to "3")
        OPTIONAL_PATTERN.parse(argList(5)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2", "d" to "3", "e" to "4")

        ENDLESS_FRONT_REQUIRED_PATTERN.parse(argList(3)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2")
        ENDLESS_FRONT_REQUIRED_PATTERN.parse(argList(4)) shouldBe mapOf("a" to "0 1", "b" to "2", "c" to "3")
        ENDLESS_FRONT_REQUIRED_PATTERN.parse(argList(5)) shouldBe mapOf("a" to "0 1 2", "b" to "3", "c" to "4")

        ENDLESS_MIDDLE_REQUIRED_PATTERN.parse(argList(3)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2")
        ENDLESS_MIDDLE_REQUIRED_PATTERN.parse(argList(4)) shouldBe mapOf("a" to "0", "b" to "1 2", "c" to "3")
        ENDLESS_MIDDLE_REQUIRED_PATTERN.parse(argList(5)) shouldBe mapOf("a" to "0", "b" to "1 2 3", "c" to "4")

        ENDLESS_END_REQUIRED_PATTERN.parse(argList(3)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2")
        ENDLESS_END_REQUIRED_PATTERN.parse(argList(4)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2 3")
        ENDLESS_END_REQUIRED_PATTERN.parse(argList(5)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2 3 4")

        ENDLESS_FRONT_OPTIONAL_PATTERN.parse(argList(2)) shouldBe mapOf("b" to "0", "c" to "1")
        ENDLESS_FRONT_OPTIONAL_PATTERN.parse(argList(3)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2")
        ENDLESS_FRONT_OPTIONAL_PATTERN.parse(argList(4)) shouldBe mapOf("a" to "0 1", "b" to "2", "c" to "3")
        ENDLESS_FRONT_OPTIONAL_PATTERN.parse(argList(5)) shouldBe mapOf("a" to "0 1 2", "b" to "3", "c" to "4")

        ENDLESS_MIDDLE_OPTIONAL_PATTERN.parse(argList(2)) shouldBe mapOf("a" to "0", "c" to "1")
        ENDLESS_MIDDLE_OPTIONAL_PATTERN.parse(argList(3)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2")
        ENDLESS_MIDDLE_OPTIONAL_PATTERN.parse(argList(4)) shouldBe mapOf("a" to "0", "b" to "1 2", "c" to "3")
        ENDLESS_MIDDLE_OPTIONAL_PATTERN.parse(argList(5)) shouldBe mapOf("a" to "0", "b" to "1 2 3", "c" to "4")

        ENDLESS_END_OPTIONAL_PATTERN.parse(argList(2)) shouldBe mapOf("a" to "0", "b" to "1")
        ENDLESS_END_OPTIONAL_PATTERN.parse(argList(3)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2")
        ENDLESS_END_OPTIONAL_PATTERN.parse(argList(4)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2 3")
        ENDLESS_END_OPTIONAL_PATTERN.parse(argList(5)) shouldBe mapOf("a" to "0", "b" to "1", "c" to "2 3 4")
    }

    private fun argList(size: Int) = List(size) { it.toString() }

    companion object {
        val BASIC_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a"),
                ArgumentDefinition("b")
            )
        )

        val OPTIONAL_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a", optional = true),
                ArgumentDefinition("b"),
                ArgumentDefinition("c", optional = true),
                ArgumentDefinition("d"),
                ArgumentDefinition("e", optional = true)
            )
        )

        val ENDLESS_FRONT_REQUIRED_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a", single = false),
                ArgumentDefinition("b"),
                ArgumentDefinition("c")
            )
        )

        val ENDLESS_END_REQUIRED_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a"),
                ArgumentDefinition("b"),
                ArgumentDefinition("c", single = false)
            )
        )

        val ENDLESS_MIDDLE_REQUIRED_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a"),
                ArgumentDefinition("b", single = false),
                ArgumentDefinition("c")
            )
        )

        val ENDLESS_FRONT_OPTIONAL_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a", single = false, optional = true),
                ArgumentDefinition("b"),
                ArgumentDefinition("c")
            )
        )

        val ENDLESS_END_OPTIONAL_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a"),
                ArgumentDefinition("b"),
                ArgumentDefinition("c", single = false, optional = true)
            )
        )

        val ENDLESS_MIDDLE_OPTIONAL_PATTERN = ArgumentPattern(
            listOf(
                ArgumentDefinition("a"),
                ArgumentDefinition("b", single = false, optional = true),
                ArgumentDefinition("c")
            )
        )
    }
}