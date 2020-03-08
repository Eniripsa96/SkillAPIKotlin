package com.sucy.skill.facade.api.dependency.api

import com.sucy.skill.facade.api.entity.Player

interface PlaceholderAPI {
    fun format(placeholder: String, player: Player): String
}