package com.sucy.skill.util.collection

import io.kotlintest.shouldBe
import org.junit.Test

class CircularSequenceTest {
    private val subject = CircularSequence(sequenceOf("a", "b", "c"))

    @Test
    fun iterationSucceeds() {
        subject.next() shouldBe "a"
        subject.next() shouldBe "b"
        subject.next() shouldBe "c"
        subject.next() shouldBe "a"
        subject.next() shouldBe "b"
    }

    @Test
    fun getValues() {
        subject.getValues(5) shouldBe listOf("a", "b", "c", "a", "b")
        subject.getValues(5) shouldBe listOf("c", "a", "b", "c", "a")
    }
}