package com.sucy.skill.util

import com.sucy.skill.facade.api.event.actor.DamageSource
import io.kotlintest.shouldBe
import org.junit.Test

class JavaExtensionsKtTest {

    @Test
    fun match() {
        DamageSource::class.match("junk", DamageSource.SKILL) shouldBe DamageSource.SKILL
        DamageSource::class.match("attack", DamageSource.SKILL) shouldBe DamageSource.ATTACK
    }
}