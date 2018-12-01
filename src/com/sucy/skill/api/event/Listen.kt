package com.sucy.skill.api.event

/**
 * SkillAPIKotlin © 2018
 */
annotation class Listen(
        val step: Step = Step.NORMAL,
        val ignoreCancelled: Boolean = false)