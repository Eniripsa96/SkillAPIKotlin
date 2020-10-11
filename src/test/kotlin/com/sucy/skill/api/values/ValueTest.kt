package com.sucy.skill.api.values

import io.kotlintest.shouldBe
import org.junit.Before
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class ValueTest {

    private lateinit var subject: Value

    @Before
    fun setUp() {
        subject = Value()
    }

    @Test
    fun getTotal() {
        subject.total shouldBe 0.0
    }

    @Test
    fun getTotalAllPieces() {
        subject.addBase(3.0, SOURCE)
        subject.addBonus(4.0, SOURCE)
        subject.addMultiplier(5.0, SOURCE)
        subject.total shouldBe 19.0
    }

    @Test
    fun addBaseOneSource() {
        subject.addBase(5.0, SOURCE)
        subject.total shouldBe 5.0
    }

    @Test
    fun addBaseMultipleSources() {
        subject.addBase(5.0, SOURCE)
        subject.addBase(3.0, "other")
        subject.total shouldBe 8.0
    }

    @Test
    fun addBaseOverlappingSource() {
        subject.addBase(5.0, SOURCE)
        subject.addBase(3.0, SOURCE)
        subject.total shouldBe 3.0
    }

    @Test
    fun addBonusOneSource() {
        subject.addBonus(5.0, SOURCE)
        subject.total shouldBe 5.0
    }

    @Test
    fun addBonusMultipleSources() {
        subject.addBonus(5.0, SOURCE)
        subject.addBonus(3.0, "other")
        subject.total shouldBe 8.0
    }

    @Test
    fun addBonusOverlappingSource() {
        subject.addBonus(5.0, SOURCE)
        subject.addBonus(3.0, SOURCE)
        subject.total shouldBe 3.0
    }

    @Test
    fun addMultiplierOneSource() {
        subject.addBase(5.0, SOURCE)
        subject.addMultiplier(1.4, SOURCE)
        subject.total shouldBe 7.0
    }

    @Test
    fun addMultiplierMultipleSources() {
        subject.addBase(5.0, SOURCE)
        subject.addMultiplier(1.4, SOURCE)
        subject.addMultiplier(2.0, "other")
        subject.total shouldBe 14.0
    }

    @Test
    fun addMultiplierOverlappingSource() {
        subject.addBase(5.0, SOURCE)
        subject.addMultiplier(1.4, SOURCE)
        subject.addMultiplier(2.0, SOURCE)
        subject.total shouldBe 10.0
    }

    @Test
    fun clear() {
        getTotalAllPieces()
        subject.clear(SOURCE)
        subject.total shouldBe 0.0
    }

    @Test
    fun clearDifferentSource() {
        getTotalAllPieces()
        subject.clear("other")
        subject.total shouldBe 19.0
    }

    @Test
    fun clearByPrefix() {
        getTotalAllPieces()
        subject.clearByPrefix("test")
        subject.total shouldBe 0.0
    }

    @Test
    fun clearByPrefixNoMatch() {
        getTotalAllPieces()
        subject.clearByPrefix("other")
        subject.total shouldBe 19.0
    }

    @Test
    fun clearByPrefixPartialMatch() {
        getTotalAllPieces()
        subject.addBase(7.0, "other")
        subject.clearByPrefix("test")
        subject.total shouldBe 7.0
    }

    companion object {
        private const val SOURCE = "test-source"
    }
}