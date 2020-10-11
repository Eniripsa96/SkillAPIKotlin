package com.sucy.skill.util.text

import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class FilterTest {
    private val subject = Filter
    private val filters = mapOf(Pair("test", "expected"))

    @Test
    fun applyNoOp() {
        val str = "test text without filters being unchanged"
        subject.apply(str, filters) shouldBe str
    }

    @Test
    fun applyNoOpWithBraces() {
        val str = "test text {with} no {matching} filters leaving unchanged"
        subject.apply(str, filters) shouldBe str
    }

    @Test
    fun applyOneApply() {
        subject.apply("As {test}!", filters) shouldBe "As expected!"
    }

    @Test
    fun applyTwoApply() {
        subject.apply("As {test}, {test}!", filters) shouldBe "As expected, expected!"
    }

    @Test
    fun applyAtStart() {
        subject.apply("{test} replacement", filters) shouldBe "expected replacement"
    }

    @Test
    fun applyAtEnd() {
        subject.apply("Replacement {test}", filters) shouldBe "Replacement expected"
    }
}