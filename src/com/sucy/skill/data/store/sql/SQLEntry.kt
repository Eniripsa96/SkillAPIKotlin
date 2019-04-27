package com.sucy.skill.data.store.sql

import com.sucy.skill.util.log.Logger
import java.lang.Exception

class SQLEntry(private val database: SQLDatabase, private val table: SQLTable, private val name: String) {
    fun getString(key: String): String {
        val result = table.query(name)
        result.next()
        val value = result.getString(key)
        result.close()
        return value.replace('`', '\'')
    }

    fun set(key: String, value: String) {
        try {
            val adjusted = value.replace('\'', '`')
            database.getStatement().execute("UPDATE ${table.name} SET $key='$adjusted' WHERE name='$name'")
        } catch (ex: Exception) {
            Logger.error("Failed to set a value for entry $name in table ${table.name}: ${ex.message}")
        }
    }
}
