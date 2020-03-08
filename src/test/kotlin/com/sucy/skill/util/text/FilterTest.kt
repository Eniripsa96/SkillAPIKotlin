package com.sucy.skill.util.text

import org.junit.Test
import kotlin.test.assertEquals

/**
 * SkillAPIKotlin © 2018
 */
class FilterTest {
    private val subject = Filter
    private val filters = mapOf(Pair("test", "expected"))

    @Test
    fun applyNoOp() {
        val str = "test text without filters being unchanged"
        assertEquals(str, subject.apply(str, filters))
    }

    @Test
    fun applyNoOpWithBraces() {
        val str = "test text {with} no {matching} filters leaving unchanged"
        assertEquals(str, subject.apply(str, filters))
    }

    @Test
    fun applyOneApply() {
        assertEquals("As expected!", subject.apply("As {test}!", filters))
    }

    @Test
    fun applyTwoApply() {
        assertEquals("As expected, expected!", subject.apply("As {test}, {test}!", filters))
    }

    @Test
    fun applyAtStart() {
        assertEquals("expected replacement", subject.apply("{test} replacement", filters))
    }

    @Test
    fun applyAtEnd() {
        assertEquals("Replacement expected", subject.apply("Replacement {test}", filters))
    }
}