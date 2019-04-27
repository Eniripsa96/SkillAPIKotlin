package com.sucy.skill.util.io

import com.sucy.skill.util.io.parser.Parser
import com.sucy.skill.util.io.parser.Parsers
import java.io.File

/**
 * SkillAPIKotlin © 2018
 */
class Config(
        private val owner: ConfigHolder,
        val name: String,
        private val parser: Parser = Parsers.YAML.parser
) {
    private val file: File = File(owner.getConfigFolder(), "$name.yml")

    private lateinit var defaults: Data
    lateinit var data: Data

    init {
        reload()
    }

    fun checkDefaults(trim: Boolean = false) {
        data.applyDefaults(defaults)
        if (trim) {
            data.trim(defaults)
        }
    }

    fun save() {
        parser.save(data, file)
    }

    fun reload() {
        file.parentFile.mkdirs()
        defaults = parser.load(owner, "$name.yml")
        if (!file.exists()) {
            parser.save(defaults, file)
        }
        data = parser.load(file)
    }
}