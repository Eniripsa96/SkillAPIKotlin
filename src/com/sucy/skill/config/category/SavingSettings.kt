package com.sucy.skill.config.category

import com.sucy.skill.SkillAPI
import com.sucy.skill.data.store.DataStore
import com.sucy.skill.data.store.LocalStore
import com.sucy.skill.data.store.sql.SQLStore
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.io.parser.Parsers
import com.sucy.skill.util.io.parser.YAMLParser
import com.sucy.skill.util.log.Logger
import java.lang.Exception

/**
 * SkillAPIKotlin © 2018
 */
class SavingSettings(config: Data) {

    // Auto save
    val enableAutoSave = config.getBoolean("auto-save", false)
    val autoSaveIntervalInMinutes = config.getInt("minutes", 60)

    // SQL
    private val enableSqlDatabase = config.getBoolean("sql-database", false)
    private val sqlHostName = config.getString("sql-details.host", "localhost")
    private val sqlPort = config.getInt("sql-details.port", 1433)
    private val sqlDatabaseName = config.getString("sql-details.database", "default")
    private val sqlUsername = config.getString("sql-details.username", "admin")
    val sqlPassword = config.getString("sql-details.password", "password")

    val dataStore: DataStore by lazy {
        if (enableSqlDatabase) {
            try {
                SQLStore(
                        parser = Parsers.YAML.parser as YAMLParser,
                        hostName = sqlHostName,
                        port = sqlPort.toString(),
                        database = sqlDatabaseName,
                        user = sqlUsername,
                        password = sqlUsername,
                        tablePrefix = "SkillAPI"
                )

            } catch (ex: Exception) {
                Logger.error("Unable to connect to SQL database, reverting to local data")
                ex.printStackTrace()
                LocalStore(SkillAPI.plugin)
            }
        } else {
            LocalStore(SkillAPI.plugin)
        }
    }
}