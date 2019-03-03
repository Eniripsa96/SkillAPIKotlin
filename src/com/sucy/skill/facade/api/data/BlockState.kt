package com.sucy.skill.facade.api.data

interface BlockState {
    var type: String
    var data: Int

    fun update(applyPhysics: Boolean)
}