package com.sucy.skill.util.math.operator

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class ModTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals('%', Mod.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(2.0, test(Mod, 2.0, 4.0))
        Assert.assertEquals(0.0, test(Mod, 4.0, 2.0))
        Assert.assertEquals(3.0, test(Mod, 7.0, 4.0))
        Assert.assertEquals(0.23, test(Mod, 1.23, 1.0))
    }
}