package com.sucy.skill.util

import com.sucy.skill.facade.api.event.actor.DamageSource
import org.junit.Test

import org.junit.Assert.*

class JavaExtensionsKtTest {

    @Test
    fun match() {
        assertEquals(
                DamageSource.SKILL,
                DamageSource::class.match("junk", DamageSource.SKILL))

        assertEquals(
                DamageSource.ATTACK,
                DamageSource::class.match("attack", DamageSource.SKILL))
    }
}