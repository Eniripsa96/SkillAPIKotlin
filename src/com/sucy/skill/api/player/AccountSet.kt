package com.sucy.skill.api.player

/**
 * SkillAPIKotlin © 2018
 */
class AccountSet {
    val data = HashMap<Int, PlayerAccount>()
    var synchronized: Boolean = true
        private set

    private var active = 0

    val activeAccount: PlayerAccount
            get() = data.getOrPut(active) { PlayerAccount() }

    companion object {
        fun fake(): AccountSet {
            val set = AccountSet()
            set.synchronized = false
            return set
        }
    }
}