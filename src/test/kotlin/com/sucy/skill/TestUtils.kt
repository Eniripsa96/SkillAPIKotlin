package com.sucy.skill

import com.sucy.skill.util.io.Config
import com.sucy.skill.util.io.ConfigHolder
import com.sucy.skill.util.io.Data

fun loadConfig(path: String): Data {
    return Config(TestConfigHolder, path).data
}

private object TestConfigHolder : ConfigHolder {
    override fun getConfigFolder(): String = "./test-output/config"
    override fun getResourceRoot(): String = "./"
}