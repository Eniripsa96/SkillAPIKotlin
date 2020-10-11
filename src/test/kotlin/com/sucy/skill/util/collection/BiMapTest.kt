package com.sucy.skill.util.collection

import io.kotlintest.shouldBe
import org.junit.Test

class BiMapTest {
    private val subject = BiMap<String, Int>()
    private val reversed = subject.reversed()

    @Test
    fun set() {
        subject["test"] = 3
        subject["test"] shouldBe 3
        reversed[3] shouldBe "test"
    }

    @Test
    fun computeIfAbsent() {
        subject.computeIfAbsent("test") { 5 }
        subject["test"] shouldBe 5

        subject.computeIfAbsent("test") { 7 }
        subject["test"] shouldBe 5
    }
}