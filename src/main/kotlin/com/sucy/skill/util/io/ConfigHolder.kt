package com.sucy.skill.util.io

/**
 * SkillAPIKotlin © 2018
 */
interface ConfigHolder {
    /**
     * Root directory to save/load configuration files
     */
    fun getConfigFolder(): String

    /**
     * Root directory for resources within the JAR file containing this class
     */
    fun getResourceRoot(): String
}
