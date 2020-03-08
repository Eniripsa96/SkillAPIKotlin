package com.sucy.skill.data.store.sql

import com.sucy.skill.data.DataType
import com.sucy.skill.data.store.DataLockedException
import com.sucy.skill.data.store.DataStore
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.io.parser.YAMLParser
import com.sucy.skill.util.log.Logger
import java.lang.Exception
import java.util.*

class SQLStore(
        private val parser: YAMLParser,
        private val hostName: String,
        private val port: String,
        private val database: String,
        private val user: String,
        private val password: String,
        private val tablePrefix: String
) : DataStore {

    init {
        // Open/close connection on creation to verify it is valid
        openConnection(DataType.PLAYERS.key).database.closeConnection()
    }

    override fun saveAll(type: String, data: Map<UUID, Data>) {
        try {
            openConnection(type).use {
                data.forEach { uuid, data ->
                    val yaml = parser.serialize(data, STRING)
                    val entry = it.table.createEntry(uuid.toString())
                    entry.set(DATA, yaml)
                    entry.set(LOCKED, 0)
                }
            }
        } catch (ex: Exception) {
            Logger.error("Failed to save player data for ${data.keys}")
        }
    }

    override fun load(uuid: UUID, type: String): Data {
        openConnection(type).use {
            return loadOne(it, uuid)
        }
    }

    override fun save(uuid: UUID, type: String, data: Data) {
        saveAll(type, mapOf(Pair(uuid, data)))
    }

    override fun loadAll(uuids: Collection<UUID>, type: String): List<Data> {
        openConnection(type).use { connection ->
            return uuids.map { loadOne(connection, it) }
        }
    }

    private fun loadOne(connection: SQLConnection, uuid: UUID): Data {
        val entry = connection.table.createEntry(uuid.toString())
        if (entry.getInt(LOCKED) == 0) {
            throw DataLockedException("Data is currently locked by another server")
        } else {
            entry.set(LOCKED, 1)
        }
        val data = entry.getString(DATA)
        return data?.let { parser.parse(it, STRING) } ?: Data()
    }

    private fun openConnection(type: String): SQLConnection {
        val database = SQLDatabase(
                host = hostName,
                port = port,
                database = database,
                username = user,
                password = password
        )

        database.openConnection()
        val table = database.createTable("${tablePrefix}_$type")
        table.createColumn(ID, ColumnType.INCREMENT)
        table.createColumn(LOCKED, ColumnType.INT)
        table.createColumn(DATA, ColumnType.TEXT)

        return SQLConnection(database, table)
    }

    private data class SQLConnection(val database: SQLDatabase, val table: SQLTable) : AutoCloseable {
        override fun close() {
            database.closeConnection()
        }
    }

    companion object {
        const val ID = "id"
        const val DATA = "data"
        const val STRING = '√'
        const val LOCKED = "locked"
    }
}
