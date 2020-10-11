package com.sucy.skill.api.attribute

import com.nhaarman.mockitokotlin2.doReturn
import com.sucy.skill.api.values.ValueSet
import com.sucy.skill.data.loader.impl.attribute.AttributeDefinitionsDataLoader
import com.sucy.skill.dynamic.Effect
import com.sucy.skill.dynamic.EffectType
import com.sucy.skill.facade.api.entity.Actor
import com.sucy.skill.loadConfig
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AttributeDefinitionsTest {
    @Mock
    private lateinit var actor: Actor
    @Mock
    private lateinit var effect: Effect

    private lateinit var valueSet: ValueSet
    private lateinit var subject: AttributeDefinitions

    @Before
    fun setUp() {
        val config = loadConfig("attributes")
        subject = AttributeDefinitionsDataLoader.load("attributes", config)
        valueSet = ValueSet()
        doReturn(valueSet).`when`(actor).attributes
    }

    @Test
    fun getLowerCase() {
        subject["vitality"] shouldNotBe null
    }

    @Test
    fun getUpperCase() {
        subject["VITALITY"] shouldNotBe null
    }

    @Test
    fun forStat() {
        val result = subject.forStat("physical-damage")
        result.stat shouldBe "physical-damage"

        valueSet["strength"].addBase(10.0, "test")
        val scaled = result.apply(actor, 5.0)
        scaled shouldBe 6.25
    }

    @Test
    fun forStatEmpty() {
        val result = subject.forStat("junk")
        result.stat shouldBe "empty"

        val scaled = result.apply(actor, 5.0)
        scaled shouldBe 5.0
    }

    @Test
    fun forEffect() {
        doReturn(EffectType.MECHANIC).`when`(effect).type
        doReturn("damage").`when`(effect).key

        val result = subject.forEffect(effect, "value")
        result.key shouldBe "damage-value"

        valueSet["intelligence"].addBase(10.0, "test")
        val scaled = result.apply(actor, effect, 5.0)
        scaled shouldBe 6.25
    }

    @Test
    fun forEffectEmpty() {
        doReturn(EffectType.MECHANIC).`when`(effect).type
        doReturn("damage").`when`(effect).key

        val result = subject.forEffect(effect, "junk")
        result.key shouldBe "empty"

        val scaled = result.apply(actor, effect, 5.0)
        scaled shouldBe 5.0
    }

    @Test
    fun toKey() {
        subject.toKey("SPIRIT") shouldBe "spirit"
    }

    @Test
    fun toKeyNotfound() {
        subject.toKey("JUNK") shouldBe "junk"
    }
}