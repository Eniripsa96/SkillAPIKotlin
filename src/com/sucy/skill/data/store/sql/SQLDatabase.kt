package com.sucy.skill.data.store.sql

import com.sucy.skill.util.log.Logger
import java.lang.Exception
import java.sql.*

class SQLDatabase(
        private val host: String,
        private val port: String,
        private val database: String,
        private val username: String,
        private val password: String
) {
    private val tables = HashMap<String, SQLTable>()

    private val connectionURL = "jdbc:mysql://$host:$port/$database"
    private var connection: Connection? = null

    val isConnected: Boolean
        get() = connection != null

    fun openConnection(): Boolean {
        if (connection != null) return true

        return try {
            connection = DriverManager.getConnection(connectionURL, username, password)
            true
        } catch (ex: Exception) {
            Logger.error("Failed to connect to the SQL database: ${ex.message}")
            false
        }
    }

    fun closeConnection() {
        try {
            connection?.close()
            connection = null
        } catch (ex: Exception) {
            Logger.error("Could not close the SQL connection: ${ex.message}")
        }
    }

    fun prepare(query: String): PreparedStatement {
        openConnection()
        return connection!!.prepareStatement(query)
    }

    fun getStatement(): Statement {
        openConnection()
        return connection!!.createStatement()
    }

    fun getMetadata(): DatabaseMetaData {
        openConnection()
        return connection?.metaData!!
    }

    fun tableExists(name: String): Boolean {
        return if (tables.containsKey(name)) {
            true
        } else {
            val meta = getMetadata()
            val result = meta.getTables(null, null, name, null)
            val exists = result.next()
            result.close()
            exists
        }
    }

    fun createTable(name: String): SQLTable {
        openConnection()
        return if (tableExists(name)) {
            tables.computeIfAbsent(name) { SQLTable(this, it) }
        } else {
            getStatement().execute("CREATE TABLE $name (Name ${ColumnType.STRING_64.key})")
            tables.computeIfAbsent(name) { SQLTable(this, it) }
        }
    }
}