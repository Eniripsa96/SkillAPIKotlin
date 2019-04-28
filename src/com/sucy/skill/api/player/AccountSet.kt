package com.sucy.skill.api.player

/**
 * SkillAPIKotlin © 2018
 */
class AccountSet {
    val data = HashMap<Int, PlayerAccount>()
    var synchronized: Boolean = true
        private set

    private var active = -1

    val activeAccount: PlayerAccount?
            get() = data[active]

    companion object {
        val FAKE_ACCOUNT by lazy {
            val set = AccountSet()
            set.synchronized = false
            set
        }
    }
}