package com.sucy.skill.util.text

import com.google.common.collect.ImmutableMap
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.util.access.Access
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * SkillAPIKotlin © 2018
 */
class FilterTest {
    private lateinit var subject: Filter

    @Before
    fun setUp() {
        subject = Filter(makeAccess("test", "expected"))
    }

    @Test
    fun applyNoOp() {
        val str = "test text without filters being unchanged"
        assertEquals(str, subject.apply(str))
    }

    @Test
    fun applyNoOpWithBraces() {
        val str = "test text {with} no {matching} filters leaving unchanged"
        assertEquals(str, subject.apply(str))
    }

    @Test
    fun applyOneApply() {
        assertEquals("As expected!", subject.apply("As {test}!"))
    }

    @Test
    fun applyTwoApply() {
        assertEquals("As expected, expected!", subject.apply("As {test}, {test}!"))
    }

    @Test
    fun applyAtStart() {
        assertEquals("expected replacement", subject.apply("{test} replacement"))
    }

    @Test
    fun applyAtEnd() {
        assertEquals("Replacement expected", subject.apply("Replacement {test}"))
    }

    private fun makeAccess(key: String, value: String): Access {
        return Access(ImmutableMap.of(key, { context: Actor? -> value }))
    }
}