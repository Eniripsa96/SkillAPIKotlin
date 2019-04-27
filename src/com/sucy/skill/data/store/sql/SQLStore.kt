package com.sucy.skill.data.store.sql

import com.sucy.skill.SkillAPI
import com.sucy.skill.data.store.DataStore
import com.sucy.skill.util.io.Data
import com.sucy.skill.util.io.parser.YAMLParser
import java.util.*

class SQLStore(private val parser: YAMLParser, private val table: String) : DataStore {
    override fun saveAll(type: String, data: Map<UUID, Data>) {
        openConnection().use {
            data.forEach { uuid, data ->
                val yaml = parser.serialize(data, STRING)
                val entry = it.table.createEntry(uuid.toString())
                entry.set(DATA, yaml)
            }
        }
    }

    override fun load(uuid: UUID, type: String): Data {
        openConnection().use {
            return loadOne(it, uuid, type)
        }
    }

    override fun save(uuid: UUID, type: String, data: Data) {
        saveAll(type, mapOf(Pair(uuid, data)))
    }

    override fun loadAll(uuids: Collection<UUID>, type: String): List<Data> {
        openConnection().use { connection ->
            return uuids.map { loadOne(connection, it, type) }
        }
    }

    private fun loadOne(connection: SQLConnection, uuid: UUID, type: String): Data {
        val entry = connection.table.createEntry(uuid.toString())
        val data = entry.getString(DATA)
        return parser.parse(data, STRING)
    }

    private fun openConnection(): SQLConnection {
        val config = SkillAPI.settings.saving

        val database = SQLDatabase(
                host = config.sqlHostName,
                port = config.sqlPort.toString(),
                database = config.sqlDatabaseName,
                username = config.sqlUsername,
                password = config.sqlPassword
        )

        database.openConnection()
        val table = database.createTable(table)
        table.createColumn(ID, ColumnType.INCREMENT)
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
    }
}
