package com.sucy.skill.data.store

import com.sucy.skill.util.io.Config
import com.sucy.skill.util.io.ConfigHolder
import com.sucy.skill.util.io.Data
import java.util.*

class LocalStore(private val configHolder: ConfigHolder) : DataStore {
    override fun saveAll(type: String, data: Map<UUID, Data>) {
        data.forEach { uuid, data -> save(uuid, type, data) }
    }

    override fun loadAll(uuids: Collection<UUID>, type: String): List<Data> {
        return uuids.map { this.load(it, type) }
    }

    override fun load(uuid: UUID, type: String): Data {
        val config = Config(configHolder, "db/$type/$uuid.yml")
        return config.data
    }

    override fun save(uuid: UUID, type: String, data: Data) {
        val config = Config(configHolder, "db/$type/$uuid.yml")
        config.data = data
        config.save()
    }
}
