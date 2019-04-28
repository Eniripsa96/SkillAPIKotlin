package com.sucy.skill.data.store.sql

import com.sucy.skill.util.log.Logger
import java.lang.Exception
import java.lang.IllegalStateException
import java.sql.ResultSet

class SQLTable(private val database: SQLDatabase, val name: String) {
    private val queryName = database.prepare("SELECT * FROM $name WHERE Name = ?")
    private val queryAll = database.prepare("SELECT * FROM $name")
    private val createEntry = database.prepare("INSERT INTO $name (Name) VALUES (?)")
    private val deleteEntry = database.prepare("DELETE FROM $name WHERE Name = ?")

    var exists = HashSet<String>()

    fun columnExists(name: String): Boolean {
        return try {
            val metadata = database.getMetadata()
            val results = metadata.getColumns(null, null, this.name, name)
            val exists = results.next()
            results?.close()
            exists
        } catch (ex: Exception) {
            Logger.error("Unable to check if column $name exists for table ${this.name}: ${ex.message}")
            false
        }
    }

    fun createColumn(name: String, type: ColumnType) {
        if (!columnExists(name)) {
            try {
                database.getStatement().execute("ALTER TABLE ${this.name} ADD $name ${type.key}")
            } catch (ex: Exception) {
                Logger.error("Failed to create the column $name for table ${this.name}: ${ex.message}")
            }
        }
    }

    fun query(name: String): ResultSet {
        queryName.setString(1, name)
        return queryName.executeQuery()
    }

    fun queryAll(): ResultSet? {
        return try {
            queryAll.executeQuery()
        } catch (ex: Exception) {
            Logger.error("Failed to execute query for all records on table $name: ${ex.message}")
            null
        }
    }

    private fun entryExists(name: String): Boolean {
        return try {
            query(name).use {
                it.next()
            }
        } catch (ex: Exception) {
            false
        }
    }

    fun createEntry(name: String): SQLEntry {
        if (!database.isConnected) throw IllegalStateException("Cannot create an entry while not connected")
        return when {
            exists.contains(name) -> SQLEntry(database, this, name)
            entryExists(name) -> {
                exists.add(name)
                SQLEntry(database, this, name)
            }
            else -> {
                createEntry.setString(1, name)
                createEntry.execute()
                exists.add(name)
                Logger.info("Created a new table entry for $name in table ${this.name}")

                SQLEntry(database, this, name)
            }
        }
    }
}