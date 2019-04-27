package com.sucy.skill.config.category

import com.sucy.skill.util.io.Data

/**
 * SkillAPIKotlin © 2018
 */
class SavingSettings(config: Data) {

    // Auto save
    val enableAutoSave = config.getBoolean("auto-save", false)
    val autoSaveIntervalInMinutes = config.getInt("minutes", 60)

    // SQL
    val enableSqlDatabase = config.getBoolean("sql-database", false)
    val sqlHostName = config.getString("sql-details.host", "localhost")
    val sqlPort = config.getInt("sql-details.port", 1433)
    val sqlDatabaseName = config.getString("sql-details.database", "default")
    val sqlUsername = config.getString("sql-details.username", "admin")
    val sqlPassword = config.getString("sql-details.password", "password")
}