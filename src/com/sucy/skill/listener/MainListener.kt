package com.sucy.skill.listener

import com.sucy.skill.SkillAPI
import com.sucy.skill.api.event.Listen
import com.sucy.skill.api.event.Step
import com.sucy.skill.api.player.AccountSet
import com.sucy.skill.data.DataType
import com.sucy.skill.data.loader.impl.account.AccountSetDataLoader
import com.sucy.skill.data.store.DataLockedException
import com.sucy.skill.facade.api.entity.Player
import com.sucy.skill.facade.api.event.player.*
import com.sucy.skill.util.log.Logger
import java.lang.Exception
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
class MainListener : SkillAPIListener {

    override fun init() {
        SkillAPI.server.players.onlinePlayers.forEach { player ->
            loadPlayerData(player.uuid)
            initialize(player)
        }
    }

    override fun cleanup() {
        SkillAPI.server.players.onlinePlayers.forEach(this::cleanUp)
    }

    private fun cleanUp(player: Player) {
        SkillAPI.eventBus.trigger(PlayerAccountsPreUnloadEvent(player))
        savePlayerData(player)
    }

    @Listen(Step.REACT, true)
    fun onLogin(event: AsyncPlayerLoginEvent) {
        loadPlayerData(event.uuid)
    }

    @Listen
    fun onJoin(event: PlayerJoinEvent) {
        if (event.player.accounts.synchronized) {
            initialize(event.player)
        } else {
            LoadRetries(event.player.uuid, 5, 5, this::doLoad).waitForNext()
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
        }
    }

    fun savePlayerData(player: Player) {
        val activeAccount = player.activeAccount
        activeAccount.health = player.health
        activeAccount.mana = player.mana
        activeAccount.food = player.food
        activeAccount.location = player.location

        val data = AccountSetDataLoader.serialize(player.accounts)
        data.setVersion(AccountSetDataLoader.latestVersion)
        SkillAPI.settings.saving.dataStore.save(player.uuid, DataType.PLAYERS.key, data)
    }

    private fun doLoad(uuid: UUID) {
        try {
            val data = SkillAPI.settings.saving.dataStore.load(uuid, DataType.PLAYERS.key)
            val accounts = AccountSetDataLoader.transformAndLoad(uuid.toString(), data)
            SkillAPI.entityData.accounts[uuid] = accounts
        } catch (ex: DataLockedException) {
            throw ex
        } catch (ex: Exception) {
            Logger.error("Unable to load data for player with UUID $uuid")
            SkillAPI.entityData.accounts[uuid] = AccountSet()
        }
    }

    /**
     * Handles retrying SQL loading when the data is still locked by
     * another server up to a given amount of [retries].
     */
    data class LoadRetries(
            private var uuid: UUID,
            private var ticks: Long,
            private var retries: Int,
            private val handle: (UUID) -> Unit
    ) {
        /**
         * Schedules the next attempt at loading the player data
         */
        internal fun waitForNext() {
            retries--
            SkillAPI.scheduler.runAsync(ticks) { next() }
            ticks *= 2
        }

        /**
         * Tries to load player data, calling [waitForNext] if it is still locked
         * and more retries are available.
         */
        private fun next() {
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

            SkillAPI.scheduler.run { initialize(player) }
        }
    }

    companion object {
        private fun initialize(player: Player) {
            SkillAPI.entityData.playerIds.computeIfAbsent(player.uuid) { player.name }

            SkillAPI.eventBus.trigger(PlayerPreInitializeEvent(player))

            val activeAccount = player.activeAccount
            if (activeAccount.health > 0) {
                player.health = activeAccount.health
                player.location = activeAccount.location
                SkillAPI.entityData.attributes[player.uuid] = activeAccount.attributes
                SkillAPI.entityData.values[player.uuid] = activeAccount.values
            }

            SkillAPI.eventBus.trigger(PlayerPostInitializeEvent(player))
        }
    }
}