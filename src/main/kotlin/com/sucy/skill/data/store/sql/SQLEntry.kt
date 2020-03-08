package com.sucy.skill.data.store.sql

import com.sucy.skill.util.log.Logger
import java.lang.Exception
import java.sql.ResultSet

class SQLEntry(private val database: SQLDatabase, private val table: SQLTable, private val name: String) : AutoCloseable {
    private var shouldClose = false
    private val resultSet: ResultSet? by lazy {
        shouldClose = true
        try {
            val result = table.query(name)
            result.next()
            result
        } catch (ex: Exception) {
            null
        }
    }

    override fun close() {
        if (shouldClose) {
            resultSet?.close()
            shouldClose = false
        }
    }

    fun getString(key: String): String? {
        return try {
            resultSet?.getString(key)?.replace('`', '\'')
        } catch (ex: Exception) {
            null
        }
    }

    fun getInt(key: String): Int {
        return try {
            resultSet?.getInt(key) ?: 0
        } catch (ex: Exception) {
            0
        }
    }

    fun set(key: String, value: String) {
        try {
            val adjusted = value.replace('\'', '`')
            database.getStatement().execute("UPDATE ${table.name} SET $key='$adjusted' WHERE name='$name'")
        } catch (ex: Exception) {
            Logger.error("Failed to set a value for entry $name in table ${table.name}: ${ex.message}")
        }
    }

    fun set (key: String, value: Int) {
        try {
            database.getStatement().execute("UPDATE ${table.name} SET $key=$value WHERE name='$name'")
        } catch (ex: Exception) {
            Logger.error("Failed to set a value for entry $name in table ${table.name}: ${ex.message}")
        }
    }
}
