package com.sucy.skill.data.store

import com.sucy.skill.util.io.Data
import java.util.*

interface DataStore {
    fun load(uuid: UUID, type: String): Data
    fun loadAll(uuids: Collection<UUID>, type: String): List<Data>
    fun save(uuid: UUID, type: String, data: Data)
    fun saveAll(type: String, data: Map<UUID, Data>)
}