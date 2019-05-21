package com.sucy.skill.data.loader.impl.account

import com.sucy.skill.api.player.AccountSet
import com.sucy.skill.data.loader.DataLoader
import com.sucy.skill.data.loader.transform.DataTransformer
import com.sucy.skill.util.io.Data

object AccountSetDataLoader : DataLoader<AccountSet> {
    private const val ID = "uuid"
    private const val ACCOUNTS = "accounts"

    override val requiredKeys: Array<String> = arrayOf(ID, ACCOUNTS)
    override val transformers: Map<Int, DataTransformer> = mapOf()

    override fun load(key: String, data: Data): AccountSet {
        val result = AccountSet()

        val accounts = data.getSection(ACCOUNTS)!!
        accounts.forEach {
            result.data[it.toInt()] = PlayerAccountDataLoader.load(it, accounts.getOrCreateSection(it))
        }

        return result
    }

    override fun serialize(data: AccountSet): Data {
        val result = Data()

        val accounts = result.createSection(ACCOUNTS)
        data.data.forEach { index, account ->
            accounts.set(index.toString(), PlayerAccountDataLoader.serialize(account))
        }

        return result
    }
}