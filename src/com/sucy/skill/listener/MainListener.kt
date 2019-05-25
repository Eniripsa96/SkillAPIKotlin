package com.sucy.skill.listener

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.api.player.AccountSet
import com.sucy.skill.data.DataType
import com.sucy.skill.data.loader.impl.account.AccountSetDataLoader
import com.sucy.skill.data.store.DataLockedException
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.player.AsyncPlayerLoginEvent
import com.sucy.skill.facade.api.event.player.PlayerJoinEvent
import com.sucy.skill.facade.api.event.player.PlayerQuitEvent
import com.sucy.skill.util.log.Logger
import java.lang.Exception
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class MainListener : SkillAPIListener {

    override fun cleanup() {
        joinHandlers.clear()
        SkillAPI.server.players.onlinePlayers.forEach(this::cleanUp)
    }

    private fun cleanUp(player: Player) {
        clearHandlers.forEach { it(player) }
        savePlayerData(player)
    }

    @Listen(Step.REACT, true)
    fun onLogin(event: AsyncPlayerLoginEvent) {
        loadPlayerData(event.uuid)
    }

    @Listen
    fun onJoin(event: PlayerJoinEvent) {
        if (event.player.accounts.synchronized) {
            joinHandlers.forEach { it.invoke(event.player) }
        }
    }

    @Listen
    fun onQuit(event: PlayerQuitEvent) {
        cleanUp(event.player)
    }

    fun loadPlayerData(uuid: UUID) {
        try {
            doLoad(uuid)
        } catch (ex: DataLockedException) {
            // Need to wait for data to sync
            LoadRetries(uuid, 10, 3, this::doLoad).waitForNext()
        }
    }

    fun savePlayerData(player: Player) {
        val data = AccountSetDataLoader.serialize(player.accounts)
        SkillAPI.settings.saving.dataStore.save(player.uuid, DataType.PLAYERS.key, data)
    }

    private fun doLoad(uuid: UUID) {
        try {
            val data = SkillAPI.settings.saving.dataStore.load(uuid, DataType.PLAYERS.key)
            val accounts = AccountSetDataLoader.transformAndLoad(uuid.toString(), data)
            SkillAPI.entityData.accounts[uuid] = accounts
        } catch (ex: DataLockedException) {
            SkillAPI.entityData.accounts[uuid] = AccountSet.FAKE_ACCOUNT
            throw ex
        } catch (ex: Exception) {
            Logger.error("Unable to load data for player with UUID $uuid")
            SkillAPI.entityData.accounts[uuid] = AccountSet()
        }
    }

    data class LoadRetries(
            private var uuid: UUID,
            private var ticks: Long,
            private var retries: Int,
            private val handle: (UUID) -> Unit
    ) {
        fun waitForNext() {
            retries--
            SkillAPI.scheduler.runAsync(ticks) { next() }
            ticks *= 2
        }

        fun next() {
            val player = SkillAPI.server.players.getPlayer(uuid) ?: return

            try {
                handle.invoke(uuid)
            } catch (ex: DataLockedException) {
                if (retries > 0) {
                    waitForNext()
                    return
                }
                SkillAPI.entityData.accounts[uuid] = AccountSet()
            }

            SkillAPI.scheduler.run {
                joinHandlers.forEach { it.invoke(player) }
            }
        }
    }

    companion object {
        val joinHandlers = ArrayList<(Player) -> Unit>()
        val clearHandlers = ArrayList<(Player) -> Unit>()
    }
}