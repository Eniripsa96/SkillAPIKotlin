package com.sucy.skill.task

import com.sucy.skill.SkillAPI
import com.sucy.skill.facade.api.event.player.ManaSource
import com.sucy.skill.util.log.Logger
import com.sucy.skill.util.players

class ManaRegenTask : Task(
        interval = SkillAPI.settings.mana.manaRechargeFrequencyInTicks,
        runnable = {
            Logger.debug("Applying mana regen...")
            players.onlinePlayers.forEach {
                val toGain = it.activeAccount.manaRegen
                it.giveMana(toGain, ManaSource.REGEN)
            }
        }
)