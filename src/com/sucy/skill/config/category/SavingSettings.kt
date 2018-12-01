package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class SavingSettings(config: Data) {

    // Auto save
    val enableAutoSave = config.getBoolean("auto-save")
    val autoSaveIntervalInMinutes = config.getInt("minutes")

    // SQL
    val enableSqlDatabase = config.getBoolean("sql-database")
    val sqlHostName = config.getString("sql-details.host")
    val sqlPort = config.getInt("sql-details.port")
    val sqlDatabaseName = config.getString("sql-details.database")
    val sqlUsername = config.getString("sql-details.username")
    val sqlPassword = config.getString("sql-details.password")
    val sqlLoadDelayInSeconds = config.getInt("sql-details.delay")
}